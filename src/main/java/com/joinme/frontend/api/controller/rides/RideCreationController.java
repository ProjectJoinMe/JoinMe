package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.accounts.UserAccountCreation;
import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.RideCreation;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RideCreationController {

    @Autowired
    private RideCreation rideCreation;

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/api/rides/createRide", method = RequestMethod.POST)
    @ResponseBody
    public void createRide(@Valid @RequestBody Ride ride) {
        rideCreation.createRide(ride, SecurityUtil.getCurrentUsername());
    }
}
