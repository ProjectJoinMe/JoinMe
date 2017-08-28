package com.joinme.backend.test;

import java.time.LocalDateTime;

import com.joinme.backend.accounts.UserAccountCreation;
import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.dto.Gender;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.RideCreation;
import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Nicole on 23.07.2017.
 */
@Component
@Transactional
@Profile("test-data")
public class TestDataGenerator {

    private static final String TEST_1 = "test";
    private static final String TEST_2 = "test2";
    private static final String PASSWORD = "123456";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountCreation userAccountCreation;

    @Autowired
    private RideCreation rideCreation;

    @Autowired
    private RideRetrieval rideRetrieval;

    @Autowired
    private RideJoinManager rideJoinManager;

    private RideDto createdRide1;
    private RideDto createdRide2;

    @PostConstruct
    public void generateTestData() {
        logger.info("Generating test data");
        createTestUser1();
        createTestUser2();
    }

    private void createTestUser1() {
        String username = TEST_1;
        UserAccount testUser = userAccountRepository.findByUsername(username);
        if (testUser == null) {
            AccountRegistrationData accountRegistrationData = new AccountRegistrationData();
            accountRegistrationData.setUsername(username);
            accountRegistrationData.setEmail(username + "@testmail.com");
            accountRegistrationData.setPassword("123456");
            accountRegistrationData.setGender(Gender.MALE);
            accountRegistrationData.setDateOfBirth(new Date());
            accountRegistrationData.setFirstName("Günther");
            accountRegistrationData.setLastName("Stefan");

            userAccountCreation.createUser(accountRegistrationData);

            RideDto ride = new RideDto();
            ride.setProviderUsername(username);
            ride.setStart("Groß Sankt Florian");
            ride.setStartPlaceId("ChIJe2nUKqC4b0cRwGfmLVeXAAQ");
            ride.setDestination("HTBLA Kaindorf");
            ride.setDestinationPlaceId("ChIJ1wvjNMCkb0cR3kRkVkLEB7Y");
            ride.setDepartureDateTime(LocalDateTime.now().plus(3, ChronoUnit.DAYS));
            ride.setMaxPassengers(4);
            ride.setReturnDepartureDateTime(null);
            ride.setNotes("Das ist eine testfahrt");
            ride.setCreationDateTime(LocalDateTime.now());

            RideDto ride2 = new RideDto();
            ride2.setProviderUsername(username);
            ride2.setStart("Graz");
            ride2.setStartPlaceId("ChIJu2UwF4c1bkcRm93f0tGKjv4");
            ride2.setDestination("Wien");
            ride2.setDestinationPlaceId("ChIJn8o2UZ4HbUcRRluiUYrlwv0");
            ride2.setDepartureDateTime(LocalDateTime.now().plus(40, ChronoUnit.DAYS));
            ride2.setMaxPassengers(3);
            ride2.setReturnDepartureDateTime(LocalDateTime.now().plus(5, ChronoUnit.DAYS));
            ride2.setNotes("Das ist eine Testfahrt mit Rückfahrt und einer möglichst langen Beschreibung, damit man sieht wie das aussehen könnte." +
                    "\nIch habe sogar einen Zeilenbruch eingefügt und schön langsam fällt mir nichts mehr ein" +
                    "\n \nLG, Nicki");
            ride2.setCreationDateTime(LocalDateTime.now());

            createdRide1 = rideCreation.createRide(ride);
            createdRide2 = rideCreation.createRide(ride2);
        } else {
            logger.info("Skipping generation of test data as user \"" + username + "\" already exists");
        }
    }

    private void createTestUser2() {
        String username = TEST_2;
        UserAccount testUser = userAccountRepository.findByUsername(username);
        if (testUser == null) {
            AccountRegistrationData accountRegistrationData = new AccountRegistrationData();
            accountRegistrationData.setUsername(username);
            accountRegistrationData.setEmail(username + "@testmail.com");
            accountRegistrationData.setPassword(PASSWORD);
            accountRegistrationData.setGender(Gender.FEMALE);
            accountRegistrationData.setDateOfBirth(new Date());
            accountRegistrationData.setFirstName("Güntherine");
            accountRegistrationData.setLastName("Dietrich");

            userAccountCreation.createUser(accountRegistrationData);

            //rideJoinManager.joinRide(createdRide1.getId(), username);
        } else {
            logger.info("Skipping generation of test data as user \"" + username + "\" already exists");
        }
    }
}
