package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideCreation;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RideCreationController {

    @Autowired
    private RideCreation rideCreation;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/createRide", method = RequestMethod.POST)
    @ResponseBody
    public RideDto createRide(@Valid @RequestBody RideDto ride) {
        ride.setProviderUsername(SecurityUtil.getCurrentUsername());
        return rideCreation.createRide(ride);
    }
}
