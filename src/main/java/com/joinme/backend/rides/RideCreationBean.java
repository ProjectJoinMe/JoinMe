package com.joinme.backend.rides;

import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Component
@Transactional
public class RideCreationBean implements RideCreation {

    @Autowired
    private RideRepository rideRepository;

    @Override
    public void createRide(Ride ride) {
        rideRepository.save(ride);
    }
}
