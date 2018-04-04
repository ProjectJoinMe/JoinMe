package com.joinme.backend.rides.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nicole on 23.07.2017.
 */
public class RideDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String providerUsername;

    @NotNull
    @Size(min = 1, max = 4000)
    private String start;

    @NotNull
    private String startPlaceId;

    @NotNull
    @Size(min = 1, max = 4000)
    private String destination;

    @NotNull
    private String destinationPlaceId;

    private LocalDateTime creationDateTime;

    @NotNull
    private LocalDateTime departureDateTime;

    @NotNull
    private int maxPassengers;

    private int freeSeats;

    private LocalDateTime returnDepartureDateTime;

    private String notes;

    private Double pricePerPassenger;

    private RideRouteDto route;

    private List<Integer> periodicWeekDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public LocalDateTime getReturnDepartureDateTime() {
        return returnDepartureDateTime;
    }

    public void setReturnDepartureDateTime(LocalDateTime returnDepartureDateTime) {
        this.returnDepartureDateTime = returnDepartureDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public String getDestinationPlaceId() {
        return destinationPlaceId;
    }

    public void setDestinationPlaceId(String destinationPlaceId) {
        this.destinationPlaceId = destinationPlaceId;
    }

    public String getStartPlaceId() {
        return startPlaceId;
    }

    public void setStartPlaceId(String startPlaceId) {
        this.startPlaceId = startPlaceId;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Double getPricePerPassenger() {
        return pricePerPassenger;
    }

    public void setPricePerPassenger(Double pricePerPassenger) {
        this.pricePerPassenger = pricePerPassenger;
    }

    public RideRouteDto getRoute() {
        return route;
    }

    public void setRoute(RideRouteDto route) {
        this.route = route;
    }

    public boolean isPeriodic() {
        return getPeriodicWeekDays() != null
                && !getPeriodicWeekDays().isEmpty();
    }

    public List<Integer> getPeriodicWeekDays() {
        return periodicWeekDays;
    }

    public void setPeriodicWeekDays(List<Integer> periodicWeekDays) {
        this.periodicWeekDays = periodicWeekDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RideDto rideDto = (RideDto) o;

        if (id == null || rideDto.id == null) {
            return false;
        }
        return id.equals(rideDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
