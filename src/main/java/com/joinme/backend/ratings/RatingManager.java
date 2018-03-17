package com.joinme.backend.ratings;

import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.dto.RideDto;

import java.util.List;

public interface RatingManager {
    RatingDto createRatingForRideJoin(RatingDto rating);

    RatingDto getRatingById(long id);

    Double getAvgRatingForUser(String username);

    List<RatingDto> getRatingsForRide(RideDto rideDto);
}
