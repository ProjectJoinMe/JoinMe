package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideJoinDto;

import java.util.List;

public interface RideJoinManager {
    RideJoinDto joinRide(long rideId, String username);

    List<RideJoinDto> getRideJoinsForRide(long rideId);
}
