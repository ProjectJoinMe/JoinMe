package com.joinme.backend.rides.entity;

import com.joinme.backend.accounts.entity.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Nicole on 10.07.2017.
 */
@Entity
public class Ride implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private UserAccount provider;

    @Column(length = 4000, nullable = false)
    @NotNull
    @Size(min = 1, max = 4000)
    private String start;

    @Column(length = 4000, nullable = false)
    @NotNull
    @Size(min = 1, max = 4000)
    private String startPlaceId;

    @Column(length = 4000, nullable = false)
    @NotNull
    @Size(min = 1, max = 4000)
    private String destination;

    @Column(length = 4000, nullable = false)
    @NotNull
    @Size(min = 1, max = 4000)
    private String destinationPlaceId;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime departureDateTime;
    /**
     * Truncated date of departureDateTime for search
     */
    @Column(nullable = false)
    @NotNull
    private LocalDate departureDate;

    @Column(nullable = false)
    @NotNull
    private Integer maxPassengers;

    @Column
    private LocalDateTime returnDepartureDateTime;
    /**
     * Truncated date of departureDateTime for search
     */
    @Column
    private LocalDate returnDepartureDate;

    @Column(length = 4000)
    private String notes;

    @Column(nullable = false)
    private Double pricePerPassenger;

    @Column(nullable = false)
    private Double borderBoxSouthWestLat;
    @Column(nullable = false)
    private Double borderBoxSouthWestLng;
    @Column(nullable = false)
    private Double borderBoxNorthEastLat;
    @Column(nullable = false)
    private Double borderBoxNorthEastLng;

    @Column(nullable = false)
    @Lob
    private String encodedPathLocations;

    @Column
    private String periodicWeekDays;

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

    public UserAccount getProvider() {
        return provider;
    }

    public void setProvider(UserAccount provider) {
        this.provider = provider;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
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

    public Double getPricePerPassenger() {
        return pricePerPassenger;
    }

    public void setPricePerPassenger(Double pricePerPassenger) {
        this.pricePerPassenger = pricePerPassenger;
    }

    public Double getBorderBoxSouthWestLat() {
        return borderBoxSouthWestLat;
    }

    public void setBorderBoxSouthWestLat(Double borderBoxSouthWestLat) {
        this.borderBoxSouthWestLat = borderBoxSouthWestLat;
    }

    public Double getBorderBoxSouthWestLng() {
        return borderBoxSouthWestLng;
    }

    public void setBorderBoxSouthWestLng(Double borderBoxSouthWestLng) {
        this.borderBoxSouthWestLng = borderBoxSouthWestLng;
    }

    public Double getBorderBoxNorthEastLat() {
        return borderBoxNorthEastLat;
    }

    public void setBorderBoxNorthEastLat(Double borderBoxNorthEastLat) {
        this.borderBoxNorthEastLat = borderBoxNorthEastLat;
    }

    public Double getBorderBoxNorthEastLng() {
        return borderBoxNorthEastLng;
    }

    public void setBorderBoxNorthEastLng(Double borderBoxNorthEastLng) {
        this.borderBoxNorthEastLng = borderBoxNorthEastLng;
    }

    public LocalDate getReturnDepartureDate() {
        return returnDepartureDate;
    }

    public void setReturnDepartureDate(LocalDate returnDepartureDate) {
        this.returnDepartureDate = returnDepartureDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public String getEncodedPathLocations() {
        return encodedPathLocations;
    }

    public void setEncodedPathLocations(String encodedPathLocations) {
        this.encodedPathLocations = encodedPathLocations;
    }

    public String getPeriodicWeekDays() {
        return periodicWeekDays;
    }

    public void setPeriodicWeekDays(String periodicWeekDays) {
        this.periodicWeekDays = periodicWeekDays;
    }
}
