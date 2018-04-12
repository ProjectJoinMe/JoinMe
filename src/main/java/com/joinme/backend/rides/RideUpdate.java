package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideDto;
/**
 * Created by Nicole, August 2017.
 */
public interface RideUpdate {
    RideDto updateRide(RideDto ride);

    void deleteRide(long rideId);
}
