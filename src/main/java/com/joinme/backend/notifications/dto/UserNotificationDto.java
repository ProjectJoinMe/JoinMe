package com.joinme.backend.notifications.dto;

import java.time.LocalDateTime;

public class UserNotificationDto {
    private long id;
    private String username;
    private LocalDateTime creationDateTime;
    private String message;
    private UserNotificationType type;
    private boolean read;
    private TypeSpecificUserNotificationData typeSpecificData;

    public UserNotificationDto() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserNotificationType getType() {
        return type;
    }

    public void setType(UserNotificationType type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public TypeSpecificUserNotificationData getTypeSpecificData() {
        return typeSpecificData;
    }

    public void setTypeSpecificData(TypeSpecificUserNotificationData typeSpecificData) {
        this.typeSpecificData = typeSpecificData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
