package com.joinme.backend.ratings.converter;

import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.ratings.entity.Rating;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class RatingConverter {

    public List<RatingDto> toDto(List<Rating> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RatingDto toDto(Rating entity) {
        RatingDto ratingDto = new RatingDto();
        setPropertiesOnDto(ratingDto, entity);
        return ratingDto;
    }

    public void setPropertiesOnDto(RatingDto ratingDto, Rating entity) {
        ratingDto.setId(entity.getId());
        ratingDto.setRating(entity.getRating());
        ratingDto.setComment(entity.getComment());
        ratingDto.setCreationDateTime(entity.getCreationDateTime());
    }

    public Rating toEntity(RatingDto ratingDto) {
        Rating entity = new Rating();
        setPropertiesOnEntity(entity, ratingDto);
        return entity;
    }

    private void setPropertiesOnEntity(Rating entity, RatingDto ratingDto) {
        entity.setId(ratingDto.getId());
        entity.setRating(ratingDto.getRating());
        entity.setComment(ratingDto.getComment());
        entity.setCreationDateTime(ratingDto.getCreationDateTime());
    }
}
