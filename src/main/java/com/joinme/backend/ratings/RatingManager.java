package com.joinme.backend.ratings;

import com.joinme.backend.ratings.dto.RatingDto;

public interface RatingManager {
    RatingDto createRatingForRideJoin(RatingDto rating);

    RatingDto getRatingById(long id);

    Double getAvgRatingForUser(String username);
}
