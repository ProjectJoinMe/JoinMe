package com.joinme.backend.location;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Locale;

public class LatLng implements Serializable {
    /**
     * SOUTH to NORTH
     */
    public double lat;
    /**
     * WEST to EAST
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LatLng latLng = (LatLng) o;

        return new EqualsBuilder()
                .append(lat, latLng.lat)
                .append(lng, latLng.lng)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lat)
                .append(lng)
                .toHashCode();
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
