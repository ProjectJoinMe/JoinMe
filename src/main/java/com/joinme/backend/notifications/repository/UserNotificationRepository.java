package com.joinme.backend.notifications.repository;


import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.notifications.entity.UserNotification;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends CrudRepository<UserNotification, Long> {

    List<UserNotification> findByUserOrderByCreationDateTimeDesc(UserAccount user);

    List<UserNotification> findByRide(Ride ride);
}