package com.joinme.backend.rides.repository;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.entity.RideJoin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideJoinRepository extends CrudRepository<RideJoin, Long> {

    List<RideJoin> findByRideOrderByCreationDateTime(Ride ride);

    RideJoin findByPassenger(UserAccount passenger);

    RideJoin findByPassengerUsernameAndRideOrderByCreationDateTime(String passengerUsername, Ride ride);

}