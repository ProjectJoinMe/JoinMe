package com.joinme.backend.rides.entity;

import com.joinme.backend.accounts.entity.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Created by Nicole on 10.07.2017.
 */
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_user_id")
    @NotNull
    private UserAccount provider;

    @Column(length = 4000)
    @NotNull
    @Size(min = 1, max = 4000)
    private String start;

    @Column(length = 4000)
    @NotNull
    @Size(min = 1, max = 4000)
    private String destination;

    @Column
    @NotNull
    private Instant departureDateTime;

    @Column
    @NotNull
    private int maxPassengers;

    @Column
    private LocalDateTime returnDepartureDateTime;

    @Column
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Instant getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Instant departureDateTime) {
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
}
