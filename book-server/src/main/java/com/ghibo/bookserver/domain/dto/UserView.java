package com.ghibo.bookserver.domain.dto;

import lombok.Data;

@Data
public class UserView {
    private long id;
    private String username;
    private String email;
}
