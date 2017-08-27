package com.joinme.backend.rides.dto;

import com.google.maps.internal.PolylineEncoding;
import com.joinme.backend.location.BorderBox;
import com.joinme.backend.location.LatLng;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nicole on 23.07.2017.
 */
public class RideRouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String encodedPathLocations;
    private BorderBox borderBox;
    private Double suggestedPricePerPassenger;

    public List<LatLng> getPathLocations() {
        return PolylineEncoding.decode(this.encodedPathLocations)
                .stream()
                .map(LatLng::new)
                .collect(Collectors.toList());
    }

    public BorderBox getBorderBox() {
        return borderBox;
    }

    public void setBorderBox(BorderBox borderBox) {
        this.borderBox = borderBox;
    }

    public Double getSuggestedPricePerPassenger() {
        return suggestedPricePerPassenger;
    }

    public void setSuggestedPricePerPassenger(Double suggestedPricePerPassenger) {
        this.suggestedPricePerPassenger = suggestedPricePerPassenger;
    }

    public String getEncodedPathLocations() {
        return encodedPathLocations;
    }

    public void setEncodedPathLocations(String encodedPathLocations) {
        this.encodedPathLocations = encodedPathLocations;
    }
}
