package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.location.BorderBox;
import com.joinme.backend.location.LatLng;
import com.joinme.backend.location.LatLngUtils;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideRouteDto;
import com.joinme.backend.rides.dto.RideSearchFilter;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class RideRetrievalBean implements RideRetrieval {

    private static final double MIDDLE_OF_ROUTE_IN_PERCENT_OF_ROUTE = 0.5;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RideConverter rideConverter;

    @Override
    public List<RideDto> getRidesOf(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        List<Ride> rides = rideRepository.findByProviderOrderByCreationDateTimeDesc(userAccount);
        return rideConverter.toDto(rides);
    }

    @Override
    public RideDto getRideById(long id) {
        Ride ride = rideRepository.findById(id);
        if (ride == null) {
            return null;
        }
        return rideConverter.toDto(ride);
    }

    @Override
    public List<RideDto> searchRides(RideSearchFilter rideSearchFilter) {
        if (rideSearchFilter.getStartLocation() == null
                && rideSearchFilter.getDestinationLocation() == null
                && rideSearchFilter.getDate() == null) {
            throw new IllegalArgumentException("at least one argument for the search has to be provided");
        }

        List<RideDto> potentialRides = rideRepository.getRidesInFutureOrAtDate(rideSearchFilter.getDate()).stream()
                .map(rideConverter::toDto)
                .collect(Collectors.toList());

        potentialRides = filterRidesQuicklyByBorderBox(potentialRides, rideSearchFilter);

        potentialRides = potentialRides.stream()
                .filter(rideDto -> areSearchedLocationsOnRideRoute(rideDto, rideSearchFilter))
                .collect(Collectors.toList());

        // TODO sort by distance ascending for best match

        return potentialRides;
    }

    /**
     * Searches locations on the path of the ride that are near enough to the searched locations.
     *
     * @param rideDto
     * @param rideSearchFilter
     * @return
     */
    private boolean areSearchedLocationsOnRideRoute(RideDto rideDto, RideSearchFilter rideSearchFilter) {
        RideRouteDto route = rideDto.getRoute();
        List<LatLng> pathLocations = route.getPathLocations();

        Double basicAllowedDistanceFromRouteInMeters = rideSearchFilter.getBasicAllowedDistanceFromRouteInMeters();
        double additionalAllowedDeviationAtMidOfRoute = determineAdditionalAllowedDeviationAtMidOfRoute(pathLocations);

        boolean foundStart = rideSearchFilter.getStartLocation() == null;
        boolean foundDestination = rideSearchFilter.getDestinationLocation() == null;
        LatLng firstLocationOnRidePathForSearchedStartLocation = null;
        // TODO performance improvement: binary search
        for (int i = 0; i < pathLocations.size(); i++) {
            LatLng pathLocation = pathLocations.get(i);

            double percentOfRouteCompleted = ((double) i) / pathLocations.size();
            double percentOfMaxDistanceToMiddleOfRoute;
            if (percentOfRouteCompleted > MIDDLE_OF_ROUTE_IN_PERCENT_OF_ROUTE) {
                percentOfMaxDistanceToMiddleOfRoute = ((percentOfRouteCompleted - MIDDLE_OF_ROUTE_IN_PERCENT_OF_ROUTE) / MIDDLE_OF_ROUTE_IN_PERCENT_OF_ROUTE);
            } else {
                percentOfMaxDistanceToMiddleOfRoute = 1.0 - percentOfRouteCompleted / MIDDLE_OF_ROUTE_IN_PERCENT_OF_ROUTE;
            }
            Double allowedDistanceFromRouteInMeters = basicAllowedDistanceFromRouteInMeters
                    + (additionalAllowedDeviationAtMidOfRoute - additionalAllowedDeviationAtMidOfRoute * percentOfMaxDistanceToMiddleOfRoute);

            if (!foundStart) {
                double distance = LatLngUtils.distanceInMetersBetween(rideSearchFilter.getStartLocation(), pathLocation);
                if (distance <= allowedDistanceFromRouteInMeters) {
                    if (i + 1 < pathLocations.size()) {
                        // try to find nearest path location
                        double distanceOfNextLocationToSearchedStart = LatLngUtils.distanceInMetersBetween(rideSearchFilter.getStartLocation(), pathLocations.get(i + 1));
                        if (distanceOfNextLocationToSearchedStart < distance) {
                            continue;
                        }
                    }
                    foundStart = true;
                    firstLocationOnRidePathForSearchedStartLocation = pathLocation;
                }
            } else if (!foundDestination) {
                double distance = LatLngUtils.distanceInMetersBetween(rideSearchFilter.getDestinationLocation(), pathLocation);
                if (distance <= allowedDistanceFromRouteInMeters) {
                    if (firstLocationOnRidePathForSearchedStartLocation != null) {
                        if (LatLngUtils.distanceInMetersBetween(rideSearchFilter.getDestinationLocation(), firstLocationOnRidePathForSearchedStartLocation)
                                < distance) {
                            // prevent long routes from being found when a short route in the opposite direction is being searched
                            // e.g. search "Schäffern, Österreich" to "Elsenau, Österreich", Graz -> Wien should not appear
                            break;
                        }
                    }
                    foundDestination = true;
                }
            } else {
                break;
            }
        }

        return foundStart && foundDestination;
    }

    private double determineAdditionalAllowedDeviationAtMidOfRoute(List<LatLng> pathLocations) {
        LatLng startLocation = pathLocations.get(0);
        LatLng destinationLocation = pathLocations.get(pathLocations.size() - 1);
        double linearDistanceBetweenStartAndDestination = LatLngUtils.distanceInMetersBetween(startLocation, destinationLocation);
        return linearDistanceBetweenStartAndDestination / 10;
    }

    private List<RideDto> filterRidesQuicklyByBorderBox(List<RideDto> potentialRides, RideSearchFilter rideSearchFilter) {
        if (rideSearchFilter.getStartLocation() != null) {
            LatLng location = rideSearchFilter.getStartLocation();
            potentialRides = filterRidesWhereLocationNotInBorderBox(potentialRides, location, rideSearchFilter.getBasicAllowedDistanceFromRouteInMeters());
        }
        if (rideSearchFilter.getDestinationLocation() != null) {
            LatLng location = rideSearchFilter.getDestinationLocation();
            potentialRides = filterRidesWhereLocationNotInBorderBox(potentialRides, location, rideSearchFilter.getBasicAllowedDistanceFromRouteInMeters());
        }
        return potentialRides;
    }

    private List<RideDto> filterRidesWhereLocationNotInBorderBox(List<RideDto> potentialRides, LatLng location, Double allowedDistanceFromRouteInMeters) {
        return potentialRides.stream()
                .filter(rideDto -> {
                    BorderBox borderBox = rideDto.getRoute().getBorderBox();
                    BorderBox expandedBorderBox = new BorderBox(
                            LatLngUtils.addMeters(borderBox.getSouthWest(), -allowedDistanceFromRouteInMeters, -allowedDistanceFromRouteInMeters),
                            LatLngUtils.addMeters(borderBox.getNorthEast(), allowedDistanceFromRouteInMeters, allowedDistanceFromRouteInMeters)
                    );
                    return expandedBorderBox.contains(location);
                })
                .collect(Collectors.toList());
    }
}