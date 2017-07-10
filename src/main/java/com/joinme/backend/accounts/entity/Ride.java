package com.joinme.backend.accounts.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Nicole on 10.07.2017.
 */
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String start;

    @Column
    private String destination;

    @Column
    private LocalDateTime departureDateTime; //check if possible

    //@JoinColumn?
    //TODO refer to the user who provides the ride (provider)

    @Column
    private int maxPassengers;

    @Column
    private boolean periodic;

    // @List ?
    //TODO how to safe the days for the ride? (Mon, Tue, Fri??)

    @Column
    private boolean returnRide;

    @Column
    private LocalDateTime returnDepartureDateTime; //check if possible

    @Column
    private String remarks;



}
