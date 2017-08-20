package com.joinme.backend.rides.entity;

import com.joinme.backend.accounts.entity.UserAccount;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Nicole on 18.08.2017.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"passenger_username", "ride_id"})})
public class RideJoin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Ride ride;

    @ManyToOne(optional = false)
    private UserAccount passenger;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public UserAccount getPassenger() {
        return passenger;
    }

    public void setPassenger(UserAccount passenger) {
        this.passenger = passenger;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
