package com.ghibo.bookserver.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;


    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }


    @ManyToMany(mappedBy = "categories")
    private List<Book> books;
}
