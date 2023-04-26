package com.ghibo.userserver.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookView implements Serializable {

    private long id;
    private String title;


}
