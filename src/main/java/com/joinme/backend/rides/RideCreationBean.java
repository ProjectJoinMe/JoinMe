package com.joinme.backend.rides;

import com.joinme.backend.googlemaps.RideGoogleMapsRouteProcessing;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
public class RideCreationBean implements RideCreation {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideConverter rideConverter;

    @Autowired
    private RideGoogleMapsRouteProcessing rideGoogleMapsRouteProcessing;

    @Override
    public RideDto createRide(RideDto ride) {
        ride.setCreationDateTime(LocalDateTime.now());
        rideGoogleMapsRouteProcessing.fillGoogleMapsRouteInformation(ride);
        if (ride.getPricePerPassenger() == null) {
            ride.setPricePerPassenger(ride.getRoute().getSuggestedPricePerPassenger());
        }
        Ride rideEntity = rideConverter.toEntity(ride);
        rideRepository.save(rideEntity);
        return rideConverter.toDto(rideEntity);
    }
}