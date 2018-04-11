package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Nicole August 2017.
 */
@Controller
public class RideJoinController {

    @Autowired
    private RideJoinManager rideJoinManager;

    /**
     * Receives the request to join a ride by the current user.
     *
     * @param rideId the id of the ride to be joined
     * @return RideJoinDto created by joining.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{rideId}/join", method = RequestMethod.POST)
    @ResponseBody
    public RideJoinDto joinRide(@PathVariable long rideId) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return rideJoinManager.joinRide(rideId, currentUsername);
    }

    /**
     * Unjoins current user from the ride provided in the request.
     *
     * @param rideId the ride to unjoin.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{rideId}/unjoin", method = RequestMethod.POST)
    @ResponseBody
    public void unjoinRide(@PathVariable long rideId) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        rideJoinManager.unjoinRide(rideId, currentUsername);
    }

    /**
     * Returns all the RideJoins for a specific ride.
     *
     * @param id the id of the ride
     * @return List of RideJoinDto with RideJoins for the ride
     */
    @RequestMapping(value = "/api/rides/{id}/joins", method = RequestMethod.GET)
    @ResponseBody
    public List<RideJoinDto> getRideJoinsForRide(@PathVariable long id) {
        List<RideJoinDto> rideJoins = rideJoinManager.getRideJoinsForRide(id);
        return rideJoins;
    }
}
