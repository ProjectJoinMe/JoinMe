package com.joinme.frontend.api.controller.rides;

import com.joinme.backend.rides.RideRetrieval;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideSearchFilter;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.RowId;
import java.time.LocalDate;
import java.util.Date;
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

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RideDto getRideById(@PathVariable long id) {
        RideDto ride = rideRetrieval.getRideById(id);
        return ride;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/rides/search", method = RequestMethod.GET)
    @ResponseBody
    public List<RideDto> searchRides(@RequestParam(required = false) String start, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate date) {
        RideSearchFilter rideSearchFilter = new RideSearchFilter();
        rideSearchFilter.setStart(start);
        rideSearchFilter.setDestination(destination);
        rideSearchFilter.setDate(date);
        List<RideDto> rides = rideRetrieval.searchRides(rideSearchFilter);
        return rides;
    }
}
