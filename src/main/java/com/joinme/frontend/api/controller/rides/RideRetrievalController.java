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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicole August 2018.
 */
@Controller
public class RideRetrievalController {

    @Autowired
    private RideRetrieval rideRetrieval;

    @Autowired
    private RideJoinManager rideJoinManager;

    @Autowired
    private RideGoogleMapsRouteProcessing rideGoogleMapsRouteProcessing;

    /**
     * Returns all rides for the user specified that he created. Actual handling done in RideRetrieval.
     *
     * @param username The username of the user requesting.
     * @return List of RideDto for the users rides.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/rides", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> getRidesOf(@PathVariable String username) {
        List<RideDto> rides = rideRetrieval.getRidesOf(username);
        return rides;
    }

    /**
     * eturns all rides for the user specified that he joined. Actual handling done in RideRetrieval.
     *
     * @param username The username of the user requesting.
     * @return List of RideDto for the users joined rides.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/joins", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> getJoinedRides(@PathVariable String username) {
        List<RideDto> rides = rideJoinManager.getJoinedRidesOf(username);
        return rides;
    }

    /**
     * Receives the request to return a ride by its id.
     *
     * @param id The id of the ride requested.
     * @return The RideDto of the ride.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RideDto getRideById(@PathVariable long id) {
        RideDto ride = rideRetrieval.getRideById(id);
        return ride;
    }

    /**
     * Receives the request to search for a ride by the criteria provided.
     * Adjusts rideSearchFilter if necessary.
     *
     * @param rideSearchFilter the filtering criteria for the rides.
     * @return the rides in question in a List of RideDto
     * @see RideSearchFilter
     */
    @RequestMapping(value = "/api/rides/search", method = RequestMethod.POST)
    @ResponseBody
    public List<RideDto> searchRides(@RequestBody RideSearchFilter rideSearchFilter) {
        if (rideSearchFilter.getBasicAllowedDistanceFromRouteInMeters() == null) {
            rideSearchFilter.setBasicAllowedDistanceFromRouteInMeters(3000d);
        }
        return rideRetrieval.searchRides(rideSearchFilter);
    }

    @RequestMapping(value = "/api/rides/searchWithStringBody", method = RequestMethod.POST)
    @ResponseBody
    public List<RideDto> searchRides(@RequestBody String lul) {
        return new ArrayList<>();
    }

    /**
     * Receives the request to fill a RideDto with information from Google Maps.
     * Actual handing done in RideGoogleMapsRouteProcessing.
     *
     * @param ride
     * @return
     */
    @RequestMapping(value = "/api/rides/routeInformation", method = RequestMethod.POST)
    @ResponseBody
    public RideDto getRouteInformation(@RequestBody RideDto ride) {
        rideGoogleMapsRouteProcessing.fillGoogleMapsRouteInformation(ride);
        return ride;
    }
}
