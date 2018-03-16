package com.joinme.backend.ratings.repository;

import com.joinme.backend.ratings.entity.Rating;
import com.joinme.backend.rides.entity.RideJoin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    Rating findById(long id);
}
