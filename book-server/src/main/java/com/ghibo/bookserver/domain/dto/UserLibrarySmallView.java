package com.ghibo.bookserver.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLibrarySmallView implements Serializable {
    private long id;

    private String name;

    private int count;

    private long userId;
}
