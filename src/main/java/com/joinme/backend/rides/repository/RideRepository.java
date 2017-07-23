package com.joinme.backend.rides.repository;


import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {

    List<Ride> findByProviderOrderByCreationDateTimeDesc(UserAccount provider);
}