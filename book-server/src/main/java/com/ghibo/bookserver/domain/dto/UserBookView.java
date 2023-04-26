package com.ghibo.bookserver.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserBookView {
    private int pagesRead;

    private int status;

    private Date startDate;

    private Date completedDate;
}
