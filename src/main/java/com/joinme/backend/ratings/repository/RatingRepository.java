package com.joinme.backend.ratings.repository;

import com.joinme.backend.ratings.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    Rating findById(long id);
}
