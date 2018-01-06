package com.joinme.frontend.api.controller.ratings;

import com.joinme.backend.ratings.RatingManager;
import com.joinme.backend.ratings.dto.RatingDto;
import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RatingController {

    @Autowired
    private RatingManager ratingManager;

    @Autowired
    private RideJoinManager rideJoinManager;

    @Autowired
    private RideRetrieval rideRetrieval;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/ratings/create/{rideJoinId}", method = RequestMethod.POST)
    @ResponseBody
    public RatingDto createRatingForRideJoin(@PathVariable long rideJoinId, @Valid @RequestBody RatingDto rating) {
        //TODO check if all checks are valid
        System.out.println(rideJoinId);
        RideJoinDto rideJoinDto = rideJoinManager.getRideJoinById(rideJoinId);

        Assert.isTrue(rideJoinDto.getUserProfileDto().getUsername().equals(SecurityUtil.getCurrentUsername()));
        Assert.isTrue(rideJoinManager.getJoinedRidesOf(SecurityUtil.getCurrentUsername()).contains(
                rideRetrieval.getRideById(rideJoinDto.getRideId()))); //checks if user is joined, TODO check if equals is needed
        Assert.isTrue(rideJoinManager.getRideJoinById(rideJoinDto.getId()).getRatingDto() == null);


        RatingDto ratingDto = ratingManager.createRatingForRideJoin(rating);
        rideJoinManager.setRating(rideJoinDto, ratingDto);
        return ratingDto;
    }


}