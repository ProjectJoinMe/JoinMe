package com.joinme.backend.location;

public class LatLngUtils {

    private static final int EARTH_RADIUS_IN_KM = 6373;

    public static double calculateDistanceInMetersBetween(LatLng p1, LatLng p2) {
        // https://stackoverflow.com/questions/120283/how-can-i-measure-distance-and-create-a-bounding-box-based-on-two-latitudelongi

        double lat1rad = Math.toRadians(p1.lat);
        double lat2rad = Math.toRadians(p2.lat);
        double deltaLat = Math.toRadians(p2.lat - p1.lat);
        double deltaLon = Math.toRadians(p2.lng - p1.lng);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1rad) * Math.cos(lat2rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_IN_KM * c * 1000;
    }
}
