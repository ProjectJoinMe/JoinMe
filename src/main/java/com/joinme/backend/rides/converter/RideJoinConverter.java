package com.joinme.backend.rides.converter;

import com.joinme.backend.accounts.UserProfileManager;
import com.joinme.backend.ratings.converter.RatingConverter;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.backend.rides.entity.RideJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Created by Nicole, August 2017.
 */
@Component
public class RideJoinConverter {

    @Autowired
    private UserProfileManager userProfileManager;

    @Autowired
    private RatingConverter ratingConverter;

    /**
     * Converts RideJoin entities to DTOs
     * @param entities
     * @return
     */
    public List<RideJoinDto> toDto(List<RideJoin> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts RideJoin entity to DTO
     * @param entity
     * @return
     */
    public RideJoinDto toDto(RideJoin entity) {
        RideJoinDto rideJoinDto = new RideJoinDto();
        setPropertiesOnDto(rideJoinDto, entity);
        return rideJoinDto;
    }

    /**
     * Sets properties of RideJoin entity on DTO
     * @param rideJoinDto
     * @param entity
     */
    public void setPropertiesOnDto(RideJoinDto rideJoinDto, RideJoin entity) {
        rideJoinDto.setId(entity.getId());
        rideJoinDto.setRideId(entity.getRide().getId());
        rideJoinDto.setUserProfileDto(userProfileManager.getProfile(entity.getPassenger().getUsername()));
        rideJoinDto.setCreationDateTime(entity.getCreationDateTime());
        rideJoinDto.setRatingDto(entity.getRating() != null ? ratingConverter.toDto(entity.getRating()) : null);
    }
}
