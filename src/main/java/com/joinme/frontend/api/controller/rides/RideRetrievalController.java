package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RideRetrievalController {

    @Autowired
    private RideRetrieval rideRetrieval;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/myRides", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> getMyRides() {
        List<RideDto> rides = rideRetrieval.getRidesOf(SecurityUtil.getCurrentUsername());
        return rides;
    }
}
