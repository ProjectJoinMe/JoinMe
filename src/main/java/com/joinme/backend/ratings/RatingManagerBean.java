package com.joinme.backend.ratings;

import com.joinme.backend.ratings.converter.RatingConverter;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.ratings.entity.Rating;
import com.joinme.backend.ratings.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Component
@Transactional
public class RatingManagerBean implements RatingManager {

    @Autowired
    private RatingConverter ratingConverter;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public RatingDto createRatingForRideJoin(RatingDto rating) {
        rating.setCreationDateTime(LocalDateTime.now());
        Rating ratingEntity = ratingConverter.toEntity(rating);

        ratingRepository.save(ratingEntity);
        return ratingConverter.toDto(ratingEntity);
    }

    @Override
    public RatingDto getRatingById(long id) {
        Rating rating = ratingRepository.findById(id);
        if (rating == null) return null;
        return ratingConverter.toDto(rating);
    }
}
