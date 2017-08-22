package com.joinme.backend.rides;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.converter.RideJoinConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.entity.RideJoin;
import com.joinme.backend.rides.repository.RideJoinRepository;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class RideJoinManagerBean implements RideJoinManager {

    @Autowired
    private RideJoinConverter rideJoinConverter;

    @Autowired
    private RideConverter rideConverter;

    @Autowired
    private RideJoinRepository rideJoinRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public RideJoinDto joinRide(long rideId, String username) {
        checkIfDuplicate(rideId, username);
        RideJoin rideJoin = new RideJoin();
        rideJoin.setRide(rideRepository.findById(rideId));
        rideJoin.setPassenger(userAccountRepository.findByUsername(username));
        rideJoin.setCreationDateTime(LocalDateTime.now());
        rideJoinRepository.save(rideJoin);
        return rideJoinConverter.toDto(rideJoin);
    }

    @Override
    public void unjoinRide(long rideId, String username) {
        RideJoin join = getJoin(rideId, username);
        rideJoinRepository.delete(join);
    }

    @Override
    public List<RideJoinDto> getRideJoinsForRide(long rideId) {
        List<RideJoin> rideJoins = rideJoinRepository.findByRideOrderByCreationDateTime(rideRepository.findById(rideId));
        return rideJoinConverter.toDto(rideJoins);
    }

    @Override
    public List<RideDto> getJoinedRidesOf(String username) {
        List<RideJoin> rideJoins = rideJoinRepository.findByPassengerUsernameOrderByCreationDateTime(username);
        List<Ride> joinedRides = rideJoins.stream()
                .map(rideJoin -> rideJoin.getRide())
                .collect(Collectors.toList());
        return rideConverter.toDto(joinedRides);
    }

    private void checkIfDuplicate(long rideId, String username) {
        RideJoin existingJoin = getJoin(rideId, username);
        if (existingJoin != null) {
            throw new IllegalArgumentException("Already joined this ride");
        }
    }

    private RideJoin getJoin(long rideId, String username) {
        return rideJoinRepository.findByPassengerUsernameAndRideOrderByCreationDateTime(username, rideRepository.findById(rideId));
    }
}
