package com.joinme.backend.rides.dto;

import com.joinme.backend.location.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nicole on 23.07.2017.
 */
public class RideRouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<LatLng> stepLocations;

    public List<LatLng> getStepLocations() {
        return stepLocations;
    }

    public void setStepLocations(List<LatLng> stepLocations) {
        this.stepLocations = stepLocations;
    }
}
