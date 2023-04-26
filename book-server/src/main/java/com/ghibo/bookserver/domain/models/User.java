package com.ghibo.bookserver.domain.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private long id;
    private String username;
    private String email;
    private String password;
}
