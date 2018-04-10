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

/**
 * Created by Nicole August 2018.
 */
@Controller
public class RideUpdateController {

    @Autowired
    private RideUpdate rideUpdate;

    /**
     * Receives request to update ride with new information provided. Forwards to RideUpdate.
     *
     * @param id   of the ride that is to be updated.
     * @param ride new information for the ride to be updated.
     * @return RideDto with the updated information
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}/updateRide", method = RequestMethod.PUT)
    @ResponseBody
    public RideDto updateRide(@PathVariable long id, @Valid @RequestBody RideDto ride) {
        Assert.isTrue(ride.getId().equals(id));
        Objects.requireNonNull(ride.getId()); // checks that the id is not null
        return rideUpdate.updateRide(ride);
    }

    /**
     * Request to delete a ride is received here.
     *
     * @param id The ride that should be deleted.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteRide(@PathVariable long id) {
        rideUpdate.deleteRide(id);
    }
}
