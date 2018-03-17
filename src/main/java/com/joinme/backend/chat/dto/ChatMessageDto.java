package com.joinme.backend.chat.dto;

import com.joinme.backend.accounts.dto.UserProfileDto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChatMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    @NotNull
    private String message;

    @NotNull
    private UserProfileDto fromUser;

    @NotNull
    private UserProfileDto toUser;

    @NotNull
    private LocalDateTime creationDateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserProfileDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserProfileDto fromUser) {
        this.fromUser = fromUser;
    }

    public UserProfileDto getToUser() {
        return toUser;
    }

    public void setToUser(UserProfileDto toUser) {
        this.toUser = toUser;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageDto that = (ChatMessageDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                Objects.equals(fromUser, that.fromUser) &&
                Objects.equals(toUser, that.toUser) &&
                Objects.equals(creationDateTime, that.creationDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, message, fromUser, toUser, creationDateTime);
    }
}
