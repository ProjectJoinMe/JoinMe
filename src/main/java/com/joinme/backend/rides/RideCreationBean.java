package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

import static com.joinme.backend.rides.entity.Ride_.provider;

@Component
@Transactional
public class RideCreationBean implements RideCreation {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideConverter rideConverter;

    @Override
    public void createRide(RideDto ride) {
        ride.setCreationDateTime(Instant.now());
        Ride rideEntity = rideConverter.toEntity(ride);
        rideRepository.save(rideEntity);
    }
}