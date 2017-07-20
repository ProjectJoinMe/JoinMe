package com.joinme.backend.rides;

import com.joinme.backend.rides.entity.Ride;

public interface RideCreation {
    void createRide(Ride ride, String username);
}
