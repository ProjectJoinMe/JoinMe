package com.joinme.backend.notifications.dto;

public class RideReferenceUserNotificationData extends TypeSpecificUserNotificationData {
    private Long rideId;

    public RideReferenceUserNotificationData() {
    }

    public RideReferenceUserNotificationData(Long rideId) {
        this.rideId = rideId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
