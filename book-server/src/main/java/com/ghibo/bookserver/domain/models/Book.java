package com.ghibo.bookserver.domain.models;

import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "authors_id"))
    Set<Author> authors;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publishedDate")
    private Date publishedDate;

    @Column(name = "description", length = 9999)
    private String description;

    @Column(name = "pageCount")
    private Integer pageCount;

    public Integer getPageCount() {
        if(pageCount == null || pageCount == 0){
            return 100;
        }
        return pageCount;
    }

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    Set<Category> categories;

    @Column(name = "averageRating")
    private Double averageRating;

    @Column(name = "ratingsCount")
    private Integer ratingsCount;

    @Column(name = "language")
    private String language;

    @Column(name = "isbn_13")
    private String isbn_13;

    @Column(name = "isbn_10")
    private String isbn_10;

    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "book")
    @Filter(
            name = "userIdFilter",
            condition = "user_id = :uid"
    )
    private List<UserBook> userBookList;

    @OneToMany(mappedBy = "book")
    @Filter(
            name = "userIdFilter",
            condition = "user_id = :uid"
    )
    private List<UserFavourite> userFavouriteList;
}
