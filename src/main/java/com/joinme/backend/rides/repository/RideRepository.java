package com.joinme.backend.rides.repository;


import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {

    List<Ride> findByProviderOrderByCreationDateTimeDesc(UserAccount provider);

    Ride findById(long id);

    @Query("SELECT ride FROM Ride ride WHERE" +
            " ((:date IS NULL AND ride.departureDateTime > current_date) OR ride.departureDate = :date)")
    List<Ride> getRidesInFutureOrAtDate(@Param("date") LocalDate date);
}