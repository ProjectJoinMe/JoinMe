package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.googlemaps.RideGoogleMapsRouteProcessing;
import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.RideReferenceUserNotificationData;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.dto.UserNotificationType;
import com.joinme.backend.notifications.entity.UserNotification;
import com.joinme.backend.notifications.repository.UserNotificationRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideJoinRepository;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class RideUpdateBean implements RideUpdate {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideConverter rideConverter;

    @Autowired
    private RideJoinRepository rideJoinRepository;

    @Autowired
    private RideGoogleMapsRouteProcessing rideGoogleMapsRouteProcessing;

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    @Autowired
    private RideJoinManager rideJoinManager;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Override
    public RideDto updateRide(RideDto ride) {
        Ride rideToUpdate = rideRepository.findById(ride.getId());
        ride.setProviderUsername(rideToUpdate.getProvider().getUsername());
        ride.setCreationDateTime(rideToUpdate.getCreationDateTime());
        List<RideJoinDto> joins = rideJoinManager.getRideJoinsForRide(ride.getId());
        for (RideJoinDto join : joins) {
            createNotification(UserNotificationType.rideWasUpdated, rideToUpdate,
                    userAccountRepository.findByUsername(join.getUserProfileDto().getUsername()),
                    "Die Fahrt " + rideToUpdate.getStart() + " - " + rideToUpdate.getDestination() + " wurde vom Anbieter bearbeitet.",
                    new RideReferenceUserNotificationData(rideToUpdate.getId()));
        }
        rideGoogleMapsRouteProcessing.fillGoogleMapsRouteInformation(ride);
        rideConverter.setPropertiesOnEntity(rideToUpdate, ride);
        return ride;
    }

    @Override
    public void deleteRide(long rideId) {
        Ride rideToDelete = rideRepository.findById(rideId);
        List<RideJoinDto> joins = rideJoinManager.getRideJoinsForRide(rideId);
        rideJoinRepository.deleteByRide(rideToDelete);
        List<UserNotification> notificationsToDelete = userNotificationRepository.findByRide(rideToDelete);
        userNotificationRepository.delete(notificationsToDelete);
        rideRepository.delete(rideToDelete);
        for (RideJoinDto join : joins) {
            createNotification(UserNotificationType.joinedRideWasDeleted, rideToDelete,
                    userAccountRepository.findByUsername(join.getUserProfileDto().getUsername()),
                    "Die Fahrt " + rideToDelete.getStart() + " - " + rideToDelete.getDestination() + " wurde vom Anbieter zurückgezogen.",
                    null);
        }
    }

    private void createNotification(UserNotificationType type, Ride ride, UserAccount passenger, String message, RideReferenceUserNotificationData typeSpecificData) {
        UserNotificationDto notification = new UserNotificationDto();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTypeSpecificData(typeSpecificData);
        notification.setUsername(passenger.getUsername());
        notificationManagerBean.create(notification);
    }
}