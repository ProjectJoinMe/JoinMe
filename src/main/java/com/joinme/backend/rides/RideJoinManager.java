package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideJoinDto;

public interface RideJoinManager {
    RideJoinDto joinRide(long rideId, String username);
}
