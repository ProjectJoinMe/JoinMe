package com.joinme.backend.rides.repository;

import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.entity.RideJoin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Created by Nicole, August 2017.
 */
@Repository
public interface RideJoinRepository extends CrudRepository<RideJoin, Long> {

    List<RideJoin> findByRideOrderByCreationDateTime(Ride ride);

    List<RideJoin> findByRide(Ride ride);

    List<RideJoin> findByPassengerUsernameOrderByCreationDateTime(String passengerUsername);

    int countByRide(Ride ride);

    int deleteByRide(Ride ride);

    RideJoin findByPassengerUsernameAndRideOrderByCreationDateTime(String passengerUsername, Ride ride);

    RideJoin findById(Long id);

}