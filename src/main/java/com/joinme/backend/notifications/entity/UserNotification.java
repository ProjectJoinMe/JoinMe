package com.joinme.backend.notifications.entity;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.notifications.dto.UserNotificationType;
import com.joinme.backend.rides.entity.Ride;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * Created by Nicole, January 2018.
 */
@Entity
@Table(name = "user_notification")
public class UserNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private UserAccount user;

    @Column(length = 4000)
    private String message;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserNotificationType type;

    @ManyToOne
    private Ride ride;

    @Column(nullable = false)
    private boolean notificationRead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public UserNotificationType getType() {
        return type;
    }

    public void setType(UserNotificationType type) {
        this.type = type;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public boolean isNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(boolean notificationRead) {
        this.notificationRead = notificationRead;
    }
}
