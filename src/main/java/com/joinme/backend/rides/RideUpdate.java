package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideDto;

public interface RideUpdate {
    RideDto updateRide(RideDto ride);

    void deleteRide(long rideId);
}
