package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideUpdate;
import com.joinme.backend.rides.dto.RideDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class RideUpdateController {

    @Autowired
    private RideUpdate rideUpdate;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}/updateRide", method = RequestMethod.PUT)
    @ResponseBody
    public RideDto updateRide(@PathVariable long id, @Valid @RequestBody RideDto ride) {
        Assert.isTrue(ride.getId().equals(id));
        Objects.requireNonNull(ride.getId());
        return rideUpdate.updateRide(ride);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteRide(@PathVariable long id) {
        rideUpdate.deleteRide(id);
    }
}
