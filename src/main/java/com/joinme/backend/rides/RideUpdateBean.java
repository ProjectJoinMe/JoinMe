package com.joinme.backend.rides;

import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
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

    @Override
    public RideDto updateRide(RideDto ride) {
        Ride rideToUpdate = rideRepository.findById(ride.getId());
        ride.setCreationDateTime(rideToUpdate.getCreationDateTime());
        ride.setProviderUsername(rideToUpdate.getProvider().getUsername());
        rideConverter.setPropertiesOnEntity(rideToUpdate, ride);
        return ride;
    }
}