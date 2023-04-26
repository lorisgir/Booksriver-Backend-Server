package com.ghibo.bookserver.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user_libraries")
@Data
public class UserLibrary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_library_books",
            joinColumns = @JoinColumn(name = "user_libraries_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    @OrderColumn(name = "id")
    Set<Book> books;

    public UserLibrary(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UserLibrary() {
    }
}
