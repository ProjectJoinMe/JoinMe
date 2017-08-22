package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.dto.RideJoinDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RideJoinController {

    @Autowired
    private RideJoinManager rideJoinManager;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{rideId}/join", method = RequestMethod.POST)
    @ResponseBody
    public RideJoinDto joinRide(@PathVariable long rideId) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return rideJoinManager.joinRide(rideId, currentUsername);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{rideId}/unjoin", method = RequestMethod.POST)
    @ResponseBody
    public void unjoinRide(@PathVariable long rideId) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        rideJoinManager.unjoinRide(rideId, currentUsername);
    }

    @RequestMapping(value = "/api/rides/{id}/joins", method = RequestMethod.GET)
    @ResponseBody
    public List<RideJoinDto> getRideJoinsForRide(@PathVariable long id) {
        List<RideJoinDto> rideJoins = rideJoinManager.getRideJoinsForRide(id);
        return rideJoins;
    }
}
