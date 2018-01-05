package com.joinme.backend.accounts.dto;

import java.io.Serializable;

public class PointOfInterestDto implements Serializable{
    private String placeId;
    private String name;

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
