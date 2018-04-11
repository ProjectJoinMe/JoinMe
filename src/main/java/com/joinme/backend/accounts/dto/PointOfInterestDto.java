package com.joinme.backend.accounts.dto;

import com.joinme.backend.location.LatLng;

import java.io.Serializable;
/**
 * Created by Nicole, January 2018.
 */
public class PointOfInterestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String placeId;
    private String name;
    private LatLng location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
