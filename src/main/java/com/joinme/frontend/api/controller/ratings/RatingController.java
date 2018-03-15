package com.joinme.frontend.api.controller.ratings;

import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.dto.UserNotificationType;
import com.joinme.backend.ratings.RatingManager;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

@Controller
public class RatingController {

    @Autowired
    private RatingManager ratingManager;

    @Autowired
    private RideJoinManager rideJoinManager;

    @Autowired
    private RideRetrieval rideRetrieval;


    @Autowired
    private NotificationManagerBean notificationManagerBean;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/ratings/create/{rideJoinId}", method = RequestMethod.POST)
    @ResponseBody
    public RatingDto createRatingForRideJoin(@PathVariable long rideJoinId, @Valid @RequestBody RatingDto rating) {
        RideJoinDto rideJoinDto = rideJoinManager.getRideJoinById(rideJoinId);

        Assert.isTrue(rideJoinDto.getUserProfileDto().getUsername().equals(SecurityUtil.getCurrentUsername()));
        Assert.isTrue(rideJoinManager.getJoinedRidesOf(SecurityUtil.getCurrentUsername()).contains(
                rideRetrieval.getRideById(rideJoinDto.getRideId()))); //checks if user is joined
        Assert.isTrue(rideJoinManager.getRideJoinById(rideJoinDto.getId()).getRatingDto() == null);

        Assert.isTrue(rideJoinManager.getRideOfRideJoin(rideJoinId).getDepartureDateTime().
                isBefore(ChronoLocalDateTime.from(LocalDateTime.now())));

        RatingDto ratingDto = ratingManager.createRatingForRideJoin(rating);
        rideJoinManager.setRating(rideJoinDto, ratingDto);

        createNotification(UserNotificationType.gotRating, rideRetrieval.getRideById(rideJoinDto.getRideId()),
                "Du hast eine neue Bewertung erhalten!"); //TODO data

        return ratingDto;
    }

    private void createNotification(UserNotificationType type, RideDto ride, String message) {
        UserNotificationDto notification = new UserNotificationDto();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTypeSpecificData(null);
        notification.setUsername(ride.getProviderUsername());
        notificationManagerBean.create(notification);
    }

}