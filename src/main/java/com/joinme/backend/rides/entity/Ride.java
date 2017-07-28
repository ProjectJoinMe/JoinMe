package com.joinme.backend.rides.entity;

import com.joinme.backend.accounts.entity.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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

    @Column(length = 4000)
    @NotNull
    @Size(min = 1, max = 4000)
    private String start;

    @Column(length = 4000)
    @NotNull
    @Size(min = 1, max = 4000)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column
    @NotNull
    private LocalDateTime departureDateTime;

    @Column
    @NotNull
    private int maxPassengers;

    @Column
    private LocalDateTime returnDepartureDateTime;

    @Column
    private String notes;

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
}
