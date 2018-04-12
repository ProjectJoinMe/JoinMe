package com.joinme.backend.rides;

import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideJoinDto;

import java.util.List;
/**
 * Created by Nicole, August 2017.
 */
public interface RideJoinManager {
    RideJoinDto joinRide(long rideId, String username);

    void unjoinRide(long rideId, String username);

    List<RideJoinDto> getRideJoinsForRide(long rideId);

    List<RideDto> getJoinedRidesOf(String username);

    RideJoinDto getRideJoinById(long rideId);

    RideJoinDto setRating(RideJoinDto rideJoinDto, RatingDto ratingDto);

    RideDto getRideOfRideJoin(long rideJoinId);

}
