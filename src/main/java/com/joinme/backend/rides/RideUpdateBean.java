package com.joinme.backend.rides;

import com.joinme.backend.googlemaps.RideGoogleMapsRouteProcessing;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideJoinRepository;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RideUpdateBean implements RideUpdate {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideConverter rideConverter;

    @Autowired
    private RideJoinRepository rideJoinRepository;

    @Autowired
    private RideGoogleMapsRouteProcessing rideGoogleMapsRouteProcessing;

    @Override
    public RideDto updateRide(RideDto ride) {
        Ride rideToUpdate = rideRepository.findById(ride.getId());
        ride.setProviderUsername(rideToUpdate.getProvider().getUsername());
        ride.setCreationDateTime(rideToUpdate.getCreationDateTime());
        rideGoogleMapsRouteProcessing.fillGoogleMapsRouteInformation(ride);
        rideConverter.setPropertiesOnEntity(rideToUpdate, ride);
        return ride;
    }

    @Override
    public void deleteRide(long rideId) {
        Ride rideToDelete = rideRepository.findById(rideId);
        rideJoinRepository.deleteByRide(rideToDelete);
        rideRepository.delete(rideToDelete);
    }
}