package com.ghibo.bookserver.domain.dto;

import com.ghibo.bookserver.domain.models.Book;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserLibraryView implements Serializable {
    private long id;

    private String name;

    List<BookView> books;

    private long userId;
}
