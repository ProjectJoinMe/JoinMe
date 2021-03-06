package com.joinme.backend.location;

import java.io.Serializable;

/**
 * Created by Nicole, August 2017.
 */
public class BorderBox implements Serializable {
    private LatLng southWest;
    private LatLng northEast;

    public BorderBox() {
    }

    public BorderBox(LatLng southWest, LatLng northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
    }

    /**
     * Check if location is in Border Box
     * @param location
     * @return
     */
    public boolean contains(LatLng location) {
        return LatLngUtils.contains(this, location);
    }

    public LatLng getSouthWest() {
        return southWest;
    }

    public void setSouthWest(LatLng southWest) {
        this.southWest = southWest;
    }

    public LatLng getNorthEast() {
        return northEast;
    }

    public void setNorthEast(LatLng northEast) {
        this.northEast = northEast;
    }
}
