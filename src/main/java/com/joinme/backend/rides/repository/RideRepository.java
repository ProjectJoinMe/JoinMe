package com.joinme.backend.rides.repository;


import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {

    List<Ride> findByProviderOrderByCreationDateTimeDesc(UserAccount provider);

    Ride findById(long id);

    @Query("SELECT ride FROM Ride ride WHERE (:start IS NULL OR ride.start = :start)" +
            " AND (:destination IS NULL OR ride.destination = :destination)" +
            " AND (:date IS NULL OR ride.departureDateTime = :date)")
    List<Ride> search(@Param("start") String start, @Param("destination") String destination, @Param("date") Date date);
}