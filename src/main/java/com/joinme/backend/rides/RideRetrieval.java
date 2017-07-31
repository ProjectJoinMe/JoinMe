package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideDto;

import java.util.List;

public interface RideRetrieval {
    List<RideDto> getRidesOf(String username);

    RideDto getRideById(long id);
}
