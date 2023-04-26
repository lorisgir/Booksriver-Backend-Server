package com.ghibo.userserver.domain.dto;

import lombok.Data;

@Data
public class UserReviewView {
    private long id;
    private String text;
    private Double score;
    private long bookId;
    private UserView user;
}
