package com.joinme.backend.test;
import java.time.Instant;

import com.joinme.backend.accounts.UserAccountCreation;
import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.dto.Gender;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.RideCreation;
import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Nicole on 23.07.2017.
 */
@Component
public class TestDataGenerator {

    public static final String USERNAME = "test";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountCreation userAccountCreation;

    @Autowired
    private RideCreation rideCreation;

    @Autowired
    private RideRetrieval rideRetrieval;


    @PostConstruct
    public void generateTestData() {
        UserAccount testUser = userAccountRepository.findByUsername(USERNAME);
        if (testUser == null) {
            logger.info("Generating test data");
            AccountRegistrationData accountRegistrationData = new AccountRegistrationData();
            accountRegistrationData.setUsername(USERNAME);
            accountRegistrationData.setEmail("test@testmail.com");
            accountRegistrationData.setPassword("feedtheguh");
            accountRegistrationData.setGender(Gender.MALE);
            accountRegistrationData.setDateOfBirth(new Date());

            userAccountCreation.createUser(accountRegistrationData);

            RideDto ride = new RideDto();
            ride.setProviderUsername(USERNAME);
            ride.setStart("Groß Sankt Florian");
            ride.setDestination("HTBLA Kaindorf");
            ride.setDepartureDateTime(Instant.now().plus(3, ChronoUnit.DAYS));
            ride.setMaxPassengers(4);
            ride.setReturnDepartureDateTime(null);
            ride.setNotes("");
            ride.setCreationDateTime(Instant.now());

            rideCreation.createRide(ride);
        } else {
            logger.info("Skipping generation of test data as user \"" + USERNAME + "\" already exists");
        }
    }
}
