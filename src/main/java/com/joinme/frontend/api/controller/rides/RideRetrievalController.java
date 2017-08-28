package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.googlemaps.RideGoogleMapsRouteProcessing;
import com.joinme.backend.rides.RideJoinManager;
import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RideRetrievalController {

    @Autowired
    private RideRetrieval rideRetrieval;

    @Autowired
    private RideJoinManager rideJoinManager;

    @Autowired
    private RideGoogleMapsRouteProcessing rideGoogleMapsRouteProcessing;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/rides", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> getRidesOf(@PathVariable String username) {
        List<RideDto> rides = rideRetrieval.getRidesOf(username);
        return rides;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/joins", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> getJoinedRides(@PathVariable String username) {
        List<RideDto> rides = rideJoinManager.getJoinedRidesOf(username);
        return rides;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RideDto getRideById(@PathVariable long id) {
        RideDto ride = rideRetrieval.getRideById(id);
        return ride;
    }

    @RequestMapping(value = "/api/rides/search", method = RequestMethod.POST)
    @ResponseBody
    public List<RideDto> searchRides(@RequestBody RideSearchFilter rideSearchFilter) {
        if (rideSearchFilter.getBasicAllowedDistanceFromRouteInMeters() == null) {
            rideSearchFilter.setBasicAllowedDistanceFromRouteInMeters(3000d);
        }
        return rideRetrieval.searchRides(rideSearchFilter);
    }

    @RequestMapping(value = "/api/rides/routeInformation", method = RequestMethod.GET)
    @ResponseBody
    public RideDto getRouteInformation(@RequestBody RideDto ride) {
        rideGoogleMapsRouteProcessing.fillGoogleMapsRouteInformation(ride);
        return ride;
    }
}
