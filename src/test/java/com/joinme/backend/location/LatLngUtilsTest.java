package com.joinme.backend.location;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LatLngUtilsTest {

    private static final LatLng GR_ST_FLORIAN = new LatLng(46.824444, 15.318611);
    private static final LatLng GRAZ = new LatLng(47.0703735, 15.4394746);
    private static final double ACCEPTED_METER_DIFFERENCE = 100;
    private static final double ACCEPTED_GEOLOC_DIFF = 0.01;

    @Test
    public void distanceInMetersBetween() throws Exception {
        double distanceInMeters = LatLngUtils.distanceInMetersBetween(GR_ST_FLORIAN, GRAZ);
        // distance between GR_ST_FLORIAN and GRAZ approximately 28,82km according to google maps
        assertThat(distanceInMeters, closeTo(28820, ACCEPTED_METER_DIFFERENCE));
    }

    @Test
    public void addMetersToEast() throws Exception {
        LatLng newLocation = LatLngUtils.addMeters(GRAZ, 10000, 0);
        assertThat(newLocation.lat, closeTo(47.07276002120369, ACCEPTED_GEOLOC_DIFF));
        assertThat(newLocation.lng, closeTo(15.563872667602482, ACCEPTED_GEOLOC_DIFF));
    }

    @Test
    public void addMetersToSouth() throws Exception {
        LatLng newLocation = LatLngUtils.addMeters(GRAZ, 0, -10000);
        assertThat(newLocation.lat, closeTo(46.97862030276625, ACCEPTED_GEOLOC_DIFF));
        assertThat(newLocation.lng, closeTo(15.439160677246036, ACCEPTED_GEOLOC_DIFF));
    }

    @Test
    public void addMetersToSouthEast() throws Exception {
        LatLng newLocation = LatLngUtils.addMeters(GRAZ, 10000, -10000);
        assertThat(newLocation.lat, closeTo(46.97936924246859, ACCEPTED_GEOLOC_DIFF));
        assertThat(newLocation.lng, closeTo(15.574869777831964, ACCEPTED_GEOLOC_DIFF));
    }

    @Test
    public void isBetween() throws Exception {
        LatLng newLocation = LatLngUtils.addMeters(GRAZ, 100, 100);
        assertThat(LatLngUtils.isBetween(GRAZ, newLocation, LatLngUtils.addMeters(GRAZ, 50, 50))
                , is(true));
        assertThat(LatLngUtils.isBetween(GRAZ, newLocation, LatLngUtils.addMeters(GRAZ, -50, 50))
                , is(false));
    }

    @Test
    public void createBorderBox() throws Exception {
        LatLng southEastToGraz = LatLngUtils.addMeters(GRAZ, 5000, -500);
        BorderBox borderBox = LatLngUtils.createBorderBox(Arrays.asList(GRAZ, GR_ST_FLORIAN, southEastToGraz));
        assertThat(borderBox.getSouthWest(), equalTo(GR_ST_FLORIAN));
        assertThat(borderBox.getNorthEast(), equalTo(new LatLng(GRAZ.lat, southEastToGraz.lng)));
    }

    @Test
    public void borderBoxContainsLocation() throws Exception {
        BorderBox borderBox = LatLngUtils.createBorderBox(Arrays.asList(GRAZ, GR_ST_FLORIAN));
        assertThat(LatLngUtils.contains(borderBox, GRAZ), is(true));
        assertThat(LatLngUtils.contains(borderBox, LatLngUtils.addMeters(GRAZ, 500, 0)), is(false));
        assertThat(LatLngUtils.contains(borderBox, LatLngUtils.addMeters(GRAZ, -500, 0)), is(true));
        assertThat(LatLngUtils.contains(borderBox, LatLngUtils.addMeters(GRAZ, 0, 5)), is(false));
        assertThat(LatLngUtils.contains(borderBox, LatLngUtils.addMeters(GRAZ, 0, -5)), is(true));
    }

}