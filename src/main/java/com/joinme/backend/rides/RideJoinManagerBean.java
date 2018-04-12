package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.RideReferenceUserNotificationData;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.dto.UserNotificationType;
import com.joinme.backend.ratings.converter.RatingConverter;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.converter.RideJoinConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.entity.RideJoin;
import com.joinme.backend.rides.repository.RideJoinRepository;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Created by Nicole, August 2017.
 */
@Component
@Transactional
public class RideJoinManagerBean implements RideJoinManager {

    @Autowired
    private RideJoinConverter rideJoinConverter;

    @Autowired
    private RideConverter rideConverter;

    @Autowired
    private RideJoinRepository rideJoinRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RatingConverter ratingConverter;

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    /**
     * Creates a new RideJoin and creates a Notification for the ride provider
     * @param rideId Ride to join
     * @param username  user that wants to join
     * @return
     */
    @Override
    public RideJoinDto joinRide(long rideId, String username) {
        checkIfDuplicate(rideId, username);
        Ride ride = rideRepository.findById(rideId);
        List<RideJoin> rideJoins = rideJoinRepository.findByRide(ride);
        if (ride.getMaxPassengers() - rideJoins.size() <= 0) {
            throw new IllegalArgumentException("No free places");
        }
        RideJoin rideJoin = new RideJoin();
        rideJoin.setRide(ride);
        UserAccount passenger = userAccountRepository.findByUsername(username);
        rideJoin.setPassenger(passenger);
        rideJoin.setCreationDateTime(LocalDateTime.now());
        rideJoinRepository.save(rideJoin);
        createNotification(UserNotificationType.rideWasJoined,
                ride,
                passenger,
                " ist deiner Fahrt (von " + ride.getStart() + " nach " + ride.getDestination() + " am " + ride.getDepartureDate() + ") beigetreten.");
        return rideJoinConverter.toDto(rideJoin);
    }

    /**
     * Deletes the RideJoin and creates a Notification for the ride provider
     * @param rideId ride to leave
     * @param username user that wants to leave the ride
     */
    @Override
    public void unjoinRide(long rideId, String username) {
        RideJoin join = getJoin(rideId, username);
        Ride ride = rideRepository.findById(rideId);
        rideJoinRepository.delete(join);
        createNotification(UserNotificationType.rideWasUnjoined,
                rideRepository.findById(rideId),
                userAccountRepository.findByUsername(username),
                " hat die Teilnahme an deiner Fahrt (von " + ride.getStart() + " nach " + ride.getDestination() + " am " + ride.getDepartureDate() + ") zurückgezogen.");
    }

    /**
     * Creates a Notification for RideJoins
     * @param type 'rideWasUnjoined' for leaving rides, 'rideWasJoined' for joining
     * @param ride
     * @param passenger
     * @param messagePostfix Text to be displayed after the passenger's name
     */
    private void createNotification(UserNotificationType type, Ride ride, UserAccount passenger, String messagePostfix) {
        UserNotificationDto notification = new UserNotificationDto();
        notification.setType(type);
        notification.setMessage(passenger.getFirstName() + messagePostfix);
        notification.setTypeSpecificData(new RideReferenceUserNotificationData(ride.getId()));
        notification.setUsername(ride.getProvider().getUsername());
        notificationManagerBean.create(notification);
    }

    /**
     * Returns all RideJoins of a ride
     * @param rideId
     * @return
     */
    @Override
    public List<RideJoinDto> getRideJoinsForRide(long rideId) {
        List<RideJoin> rideJoins = rideJoinRepository.findByRideOrderByCreationDateTime(rideRepository.findById(rideId));
        return rideJoinConverter.toDto(rideJoins);
    }

    /**
     * Returns all rides a user has joined
     * @param username
     * @return
     */
    @Override
    public List<RideDto> getJoinedRidesOf(String username) {
        List<RideJoin> rideJoins = rideJoinRepository.findByPassengerUsernameOrderByCreationDateTime(username);
        List<Ride> joinedRides = rideJoins.stream()
                .map(rideJoin -> rideJoin.getRide())
                .collect(Collectors.toList());
        return rideConverter.toDto(joinedRides);
    }

    /**
     * Returns all ride joins from a ride
     * @param rideId
     * @return
     */
    @Override
    public RideJoinDto getRideJoinById(long rideId) {
        RideJoin rideJoin = rideJoinRepository.findById(rideId);
        return rideJoinConverter.toDto(rideJoin);
    }

    /**
     * Sets Rating for RideJoin
     * @param rideJoinDto
     * @param ratingDto
     * @return
     */
    @Override
    public RideJoinDto setRating(RideJoinDto rideJoinDto, RatingDto ratingDto) {
        RideJoin existingJoin = rideJoinRepository.findById(rideJoinDto.getId());
        existingJoin.setRating(ratingConverter.toEntity(ratingDto));
        return rideJoinConverter.toDto(existingJoin);
    }

    /**
     * Returns ride of RideJoin
     * @param rideJoinId
     * @return
     */
    @Override
    public RideDto getRideOfRideJoin(long rideJoinId) {
        Ride ride = rideRepository.findById(getRideJoinById(rideJoinId).getRideId());
        return rideConverter.toDto(ride);
    }

    /**
     * Checks, if user already joined that ride
     * @param rideId
     * @param username
     */
    private void checkIfDuplicate(long rideId, String username) {
        RideJoin existingJoin = getJoin(rideId, username);
        if (existingJoin != null) {
            throw new IllegalArgumentException("Already joined this ride");
        }
    }

    /**
     * Finds RideJoin for for ride and username
     * @param rideId
     * @param username
     * @return
     */
    private RideJoin getJoin(long rideId, String username) {
        return rideJoinRepository.findByPassengerUsernameAndRideOrderByCreationDateTime(username, rideRepository.findById(rideId));
    }
}
