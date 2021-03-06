package com.joinme.backend.ratings;

import com.joinme.backend.ratings.converter.RatingConverter;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.ratings.entity.Rating;
import com.joinme.backend.ratings.repository.RatingRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander, January 2018.
 */
@Component
@Transactional
public class RatingManagerBean implements RatingManager {

    @Autowired
    private RatingConverter ratingConverter;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RideConverter rideConverter;

    /**
     * Creates a new Rating for the RideJoin and saves it to the database
     * @param rating
     * @return
     */
    @Override
    public RatingDto createRatingForRideJoin(RatingDto rating) {
        rating.setCreationDateTime(LocalDateTime.now());
        Rating ratingEntity = ratingConverter.toEntity(rating);

        ratingRepository.save(ratingEntity);
        return ratingConverter.toDto(ratingEntity);
    }

    /**
     * Returns the rating by id
     * @param id
     * @return
     */
    @Override
    public RatingDto getRatingById(long id) {
        Rating rating = ratingRepository.findById(id);
        if (rating == null) return null;
        return ratingConverter.toDto(rating);
    }

    /**
     * Returns the average rating of all rides from a user
     * @param username
     * @return
     */
    @Override
    public Double getAvgRatingForUser(String username) {
        return ratingRepository.getAvgRatingForUser(username);
    }

    /**
     * Returns the rating for the ride
     * @param rideDto
     * @return
     */
    @Override
    public List<RatingDto> getRatingsForRide(RideDto rideDto) {
        Ride ride = rideConverter.toEntity(rideDto);
        List<Rating> ratingList = ratingRepository.getRatingsForRide(ride);
        return ratingList.stream().map(ratingConverter::toDto).collect(Collectors.toList());
    }
}
