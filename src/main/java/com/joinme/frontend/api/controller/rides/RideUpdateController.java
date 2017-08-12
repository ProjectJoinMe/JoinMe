package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideCreation;
import com.joinme.backend.rides.RideUpdate;
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
import java.util.Objects;

@Controller
public class RideUpdateController {

    @Autowired
    private RideUpdate rideUpdate;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/updateRide", method = RequestMethod.POST)
    @ResponseBody
    public void updateRide(@Valid @RequestBody RideDto ride) {
        Objects.requireNonNull(ride.getId());
        rideUpdate.updateRide(ride);
    }
}
