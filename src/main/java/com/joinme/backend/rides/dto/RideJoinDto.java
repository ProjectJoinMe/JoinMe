package com.joinme.backend.rides.dto;

import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.ratings.dto.RatingDto;

import java.time.LocalDateTime;
/**
 * Created by Nicole on 23.07.2017.
 */
public class RideJoinDto {

    private long id;

    private long rideId;

    private UserProfileDto userProfileDto;

    private LocalDateTime creationDateTime;

    private RatingDto ratingDto;

    public long getRideId() {
        return rideId;
    }

    public void setRideId(long rideId) {
        this.rideId = rideId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public UserProfileDto getUserProfileDto() {
        return userProfileDto;
    }

    public void setUserProfileDto(UserProfileDto userProfileDto) {
        this.userProfileDto = userProfileDto;
    }

    public RatingDto getRatingDto() {
        return ratingDto;
    }

    public void setRatingDto(RatingDto ratingDto) {
        this.ratingDto = ratingDto;
    }
}
