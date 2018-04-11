package com.joinme.backend.ratings.repository;

import com.joinme.backend.ratings.entity.Rating;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Created by Alexander, January 2018.
 */
@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    Rating findById(long id);

    @Query("SELECT SUM(r.rating) / COUNT(r) * 1.0 " +
            "FROM RideJoin rj " +
            "     JOIN rj.rating r " +
            "WHERE rj.ride.provider.username = :#{#username}")
        //* 1.0 needed for decimal
    Double getAvgRatingForUser(@Param("username") String username);

    @Query("SELECT ra " +
            "FROM RideJoin rj " +
            "     JOIN rj.ride ri " +
            "     JOIN rj.rating ra " +
            "WHERE ri.id = :#{#ride.id}")
    List<Rating> getRatingsForRide(@Param("ride") Ride ride);
}
