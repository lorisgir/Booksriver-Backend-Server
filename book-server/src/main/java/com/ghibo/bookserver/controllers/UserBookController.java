package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserBookView;
import com.ghibo.bookserver.domain.dto.UserStatusLibraryView;
import com.ghibo.bookserver.domain.dto.requests.ChangeStatusUserBookRequest;
import com.ghibo.bookserver.domain.dto.requests.ReadBookRequest;
import com.ghibo.bookserver.services.UserBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User Book")
@RestController
@RequestMapping(path = "user-book")
@RequiredArgsConstructor
public class UserBookController {

    private final UserBookService userBookService;

    @PostMapping("{idBook}/add")
    public UserBookView add(@PathVariable long idBook) {
        return userBookService.add(idBook);
    }

    @PostMapping("{idBook}/addReading")
    public UserBookView addReading(@PathVariable long idBook) {
        return userBookService.addReading(idBook);
    }


    @DeleteMapping("{idBook}/remove")
    public void remove(@PathVariable long idBook) {
        userBookService.remove(idBook);
    }

    @PostMapping("{idBook}/change-pages-read")
    public UserBookView changePagesRead(@PathVariable long idBook, @RequestBody @Valid ReadBookRequest readBookRequest) {
        return userBookService.changePagesRead(idBook, readBookRequest);
    }

    @PostMapping("{idBook}/change-status")
    public UserBookView changeStatus(@PathVariable long idBook, @RequestBody @Valid ChangeStatusUserBookRequest changeStatusUserBookRequest) {
        return userBookService.changeStatus(idBook, changeStatusUserBookRequest);
    }

    @GetMapping("{status}/books")
    public List<BookView> getBooksByStatus(@PathVariable int status) {
        return userBookService.getBooksByStatus(status);
    }

    @GetMapping("books")
    public List<BookView> getBooks() {
        return userBookService.getBooks();
    }


    @GetMapping("user/{id}/status-libraries")
    public List<UserStatusLibraryView> getStatusLibrariesByUser(@PathVariable long id) {
        return userBookService.getStatusLibrariesByUser(id);
    }

}
