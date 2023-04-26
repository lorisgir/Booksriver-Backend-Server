package com.ghibo.bookserver.domain.dto.requests;

import lombok.Data;

import java.util.List;

@Data
public class AddBookToLibrariesRequest {

    private List<Long> librariesId;
    private long bookId;

}
