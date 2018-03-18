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
import java.util.List;

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
        Assert.isTrue(rideJoinManager.getRideJoinById(rideJoinDto.getId()).getRatingDto() == null); //ensures that no rating is present

        Assert.isTrue(rideJoinManager.getRideOfRideJoin(rideJoinId).getDepartureDateTime().
                isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))); //checks that the ride has already taken place

        Assert.isTrue(rating.getRating() >= 1 && rating.getRating() <= 5); //assures that the rating is valid

        RatingDto ratingDto = ratingManager.createRatingForRideJoin(rating);
        rideJoinManager.setRating(rideJoinDto, ratingDto);

        createNotification(UserNotificationType.gotRating, rideRetrieval.getRideById(rideJoinDto.getRideId()),
                "Du hast eine neue Bewertung erhalten!");

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

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/ratings/avgRating/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Double getAvgRatingForUser(@PathVariable String username) {

        Double rating = ratingManager.getAvgRatingForUser(username);
        if (rating == null) rating = new Double(0); //if there is no rating the avg is set to zero
        return rating;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/ratings/ride/{rideId}", method = RequestMethod.GET)
    @ResponseBody
    public List<RatingDto> getRatingsForRide(@Valid @PathVariable Long rideId) {
        RideDto rideDto = rideRetrieval.getRideById(rideId);
        return ratingManager.getRatingsForRide(rideDto);
    }
}