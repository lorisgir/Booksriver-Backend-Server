package com.ghibo.bookserver.domain.models;

import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_book")
@Data
@FilterDef(
        name = "userIdFilter",
        parameters = @ParamDef(name = "uid", type = "long")
)
public class UserBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "page_read")
    private int pagesRead;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "completed_date")
    private Date completedDate;

    /**
     * 0 non iniziato
     * 1 reading
     * 2 completed
     * 3 dropped
     */
    @Column(name = "status")
    private int status;

    public UserBook() {
        this.status = 0;
        this.pagesRead = 0;
    }

    public UserBook(int status) {
        this.status = status;
        this.pagesRead = 0;
    }
}
