package com.joinme.backend.rides.repository;


import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {

}