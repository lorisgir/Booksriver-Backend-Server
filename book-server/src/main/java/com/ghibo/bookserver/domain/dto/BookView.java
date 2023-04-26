package com.ghibo.bookserver.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookView {

    private long id;
    private String title;
    private List<AuthorView> authors;
    private String publisher;
    private Date publishedDate;
    private String description;
    private Integer pageCount;
    private List<CategoryView> categories;
    private Double averageRating;
    private Integer ratingsCount;
    private String language;
    private String isbn_13;
    private String isbn_10;
    private String imageUrl;
    private UserBookView userBook;
    private boolean isFavourite;
}
