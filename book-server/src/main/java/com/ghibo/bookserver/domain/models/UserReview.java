package com.ghibo.bookserver.domain.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserReview implements Serializable {
    private long id;

    private String text;

    private long bookId;

    private Double score;
}
