package com.ghibo.userserver.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLibraryView implements Serializable {
    private long id;

    private String name;

    private int count;
}
