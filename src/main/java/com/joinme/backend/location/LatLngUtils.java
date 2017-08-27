package com.joinme.backend.location;

import org.springframework.util.Assert;

import java.util.List;

public class LatLngUtils {

    private static final int EARTH_RADIUS_IN_KM = 6373;

    public static double distanceInMetersBetween(LatLng p1, LatLng p2) {
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

    public static LatLng addMeters(LatLng location, double dxMeters, double dyMeters) {
        // https://stackoverflow.com/questions/7477003/calculating-new-longtitude-latitude-from-old-n-meters
        double dxKm = dxMeters / 1000;
        double dyKm = dyMeters / 1000;
        double lat = location.lat + (dyKm / EARTH_RADIUS_IN_KM) * (180 / Math.PI);
        double lng = location.lng + (dxKm / EARTH_RADIUS_IN_KM) * (180 / Math.PI) / Math.cos(location.lat * Math.PI / 180);
        return new LatLng(lat, lng);
    }

    public static boolean isBetween(LatLng p1, LatLng p2, LatLng test) {
        return isBetweenOrEqual(test.lat, Math.min(p1.lat, p2.lat), Math.max(p1.lat, p2.lat))
                && isBetweenOrEqual(test.lng, Math.min(p1.lng, p2.lng), Math.max(p1.lng, p2.lng));
    }

    public static boolean contains(BorderBox borderBox, LatLng test) {
        return isBetweenOrEqual(test.lat, borderBox.getSouthWest().lat, borderBox.getNorthEast().lat)
                && isBetweenOrEqual(test.lng, borderBox.getSouthWest().lng, borderBox.getNorthEast().lng);
    }

    /**
     * Creates a border box around the given locations consisting of the most south-west location and most north-west location.
     *
     * @param locations
     * @return
     */
    public static BorderBox createBorderBox(List<LatLng> locations) {
        Assert.notEmpty(locations);
        double maxLat = locations.stream()
                .map(LatLng::getLat)
                .max(Double::compare)
                .get();
        double maxLng = locations.stream()
                .map(LatLng::getLng)
                .max(Double::compare)
                .get();
        double minLat = locations.stream()
                .map(LatLng::getLat)
                .min(Double::compare)
                .get();
        double minLng = locations.stream()
                .map(LatLng::getLng)
                .min(Double::compare)
                .get();
        return new BorderBox(new LatLng(minLat, minLng), new LatLng(maxLat, maxLng));
    }

    private static boolean isBetweenOrEqual(double value, double low, double high) {
        return value >= low && value <= high;
    }
}
