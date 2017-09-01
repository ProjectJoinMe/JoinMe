package com.joinme.backend.ratings.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class RatingDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private int rating;

    private String comment;

    @NotNull
    private LocalDateTime creationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

}
