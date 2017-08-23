package com.joinme.backend.location;

import java.io.Serializable;
import java.util.Locale;

public class LatLng implements Serializable {
    public double lat;
    public double lng;

    public LatLng() {
    }

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng(com.google.maps.model.LatLng latLng) {
        this(latLng.lat, latLng.lng);
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "%f,%f", this.lat, this.lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
