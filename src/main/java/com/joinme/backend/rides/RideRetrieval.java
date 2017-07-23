package com.joinme.backend.rides;

import com.joinme.backend.rides.entity.Ride;

import java.util.List;

public interface RideRetrieval {
    List<Ride> getRidesOf(String username);
}
