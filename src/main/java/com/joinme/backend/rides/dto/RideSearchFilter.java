package com.joinme.backend.rides.dto;

import com.joinme.backend.location.LatLng;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
/**
 * Created by Nicole on 23.07.2017.
 */
public class RideSearchFilter {
    private LatLng startLocation;
    private LatLng destinationLocation;
    private Double basicAllowedDistanceFromRouteInMeters;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(LatLng destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Double getBasicAllowedDistanceFromRouteInMeters() {
        return basicAllowedDistanceFromRouteInMeters;
    }

    public void setBasicAllowedDistanceFromRouteInMeters(Double basicAllowedDistanceFromRouteInMeters) {
        this.basicAllowedDistanceFromRouteInMeters = basicAllowedDistanceFromRouteInMeters;
    }
}
