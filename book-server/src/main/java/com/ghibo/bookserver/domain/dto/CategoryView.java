package com.ghibo.bookserver.domain.dto;

import lombok.Data;

@Data
public class CategoryView {
    private long id;
    private String name;
    private int count;
}
