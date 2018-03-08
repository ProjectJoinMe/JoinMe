package com.joinme.backend.chat.entity;

import com.joinme.backend.accounts.entity.UserAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount fromUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount toUser;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

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

    public UserAccount getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserAccount fromUser) {
        this.fromUser = fromUser;
    }

    public UserAccount getToUser() {
        return toUser;
    }

    public void setToUser(UserAccount toUser) {
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
        ChatMessage that = (ChatMessage) o;
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
