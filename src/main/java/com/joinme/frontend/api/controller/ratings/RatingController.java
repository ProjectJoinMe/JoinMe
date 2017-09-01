package com.joinme.frontend.api.controller.ratings;

import com.joinme.backend.ratings.RatingManager;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.dto.RideJoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RatingController {

    @Autowired
    private RatingManager ratingManager;

    @Autowired
    private RideJoinManager rideJoinManager;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/ratings/createRatingForRideJoin", method = RequestMethod.POST)
    @ResponseBody
    public RatingDto createRatingForRideJoin(@Valid @RequestBody RatingDto rating, @RequestBody RideJoinDto rideJoin) {
        //TODO check if all is valid (user is joined etc...)
        RatingDto ratingDto = ratingManager.createRatingForRideJoin(rating);
        rideJoinManager.setRating(rideJoin, ratingDto);
        return ratingDto;
    }
}
