package com.joinme.backend.rides;

import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideSearchFilter;

import java.util.List;
/**
 * Created by Nicole, August 2017.
 */
public interface RideRetrieval {
    List<RideDto> getRidesOf(String username);

    RideDto getRideById(long id);

    List<RideDto> searchRides(RideSearchFilter rideSearchFilter);
}
