package com.ghibo.bookserver.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserStatusLibraryView implements Serializable {
    private int status;

    private String name;

    List<BookView> books;

    public UserStatusLibraryView(int status, String name) {
        this.status = status;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public UserStatusLibraryView(int status, String name, List<BookView> bookViews) {
        this.status = status;
        this.name = name;
        this.books = bookViews;
    }
}
