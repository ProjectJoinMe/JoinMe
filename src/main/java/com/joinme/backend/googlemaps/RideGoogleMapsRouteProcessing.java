package com.joinme.backend.googlemaps;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.*;
import com.joinme.backend.location.LatLng;
import com.joinme.backend.location.LatLngUtils;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideRouteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
/**
 * Created by Nicole, August 2017.
 */
@Component
public class RideGoogleMapsRouteProcessing {

    private static final double SUGGESTED_PRICE_PER_MINUTE = 0.05;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoApiContext geoApiContext;

    /**
     * Sets route information on the ride
     * @param ride
     */
    public void fillGoogleMapsRouteInformation(RideDto ride) {
        DirectionsResult directions = getDirections(ride);

        DirectionsRoute route = directions.routes[0];
        Assert.isTrue(route.legs.length == 1);
        DirectionsLeg routeLeg = route.legs[0];

        checkPlaceIdChange(ride, directions);

        ride.setRoute(createRideRouteInfo(route, routeLeg));
    }

    /**
     * Creates all RideRoute information
     * @param route
     * @param routeLeg
     * @return
     */
    private RideRouteDto createRideRouteInfo(DirectionsRoute route, DirectionsLeg routeLeg) {
        RideRouteDto rideRouteDto = new RideRouteDto();

        rideRouteDto.setEncodedPathLocations(route.overviewPolyline.getEncodedPath());
        List<LatLng> pathLocations = rideRouteDto.getPathLocations();
        rideRouteDto.setBorderBox(LatLngUtils.createBorderBox(pathLocations));

        rideRouteDto.setSuggestedPricePerPassenger(determineSuggestedPrice(routeLeg));
        return rideRouteDto;
    }

    /**
     * Check if the place id changed (Google Maps is changing placeids over time)
     * @param ride
     * @param directions
     */
    private void checkPlaceIdChange(RideDto ride, DirectionsResult directions) {
        // update place IDs as they can change over very long times // TODO periodically regenerate them as stated in https://developers.google.com/places/place-id#save-id
        if (!ride.getStartPlaceId().equals(directions.geocodedWaypoints[0].placeId)
                || !ride.getDestinationPlaceId().equals(directions.geocodedWaypoints[1].placeId)) {
            logger.warn("The primary place ID for the ride " + ride.getId() + " has changed");
            ride.setStartPlaceId(directions.geocodedWaypoints[0].placeId);
            ride.setDestinationPlaceId(directions.geocodedWaypoints[1].placeId);
        }
    }

    /**
     * Calculate suggested price for ride route based on ride duration
     * @param routeLeg
     * @return
     */
    private double determineSuggestedPrice(DirectionsLeg routeLeg) {
        long minutes = routeLeg.duration.inSeconds / 60;
        int suggestedPrice = (int) Math.round(minutes * SUGGESTED_PRICE_PER_MINUTE);
        if (suggestedPrice == 0) {
            suggestedPrice = 1;
        }
        return (double) suggestedPrice;
    }

    /**
     * Get Google Maps directions from ride
     * @param ride
     * @return
     */
    private DirectionsResult getDirections(RideDto ride) {
        try {
            return DirectionsApi.newRequest(geoApiContext)
                    .origin("place_id:" + ride.getStartPlaceId())
                    .destination("place_id:" + ride.getDestinationPlaceId())
                    .mode(TravelMode.DRIVING)
                    .units(Unit.METRIC)
                    .alternatives(false)
                    .language("de")
                    .await();
        } catch (NotFoundException e) {
            throw new IllegalArgumentException("Could not find any route for ride", e);
        } catch (InvalidRequestException e) {
            throw new IllegalArgumentException("Invalid values", e);
        } catch (ApiException e) {
            throw new IllegalStateException("google maps API call failed unexpectedly. please retry later", e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("google maps API call was interrupted. please retry", e);
        } catch (IOException e) {
            throw new IllegalStateException("google maps API call failed unexpectedly. please retry", e);
        }
    }
}
