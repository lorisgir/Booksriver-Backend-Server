package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserLibrarySmallView;
import com.ghibo.bookserver.domain.dto.UserLibraryView;
import com.ghibo.bookserver.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book")
@RestController
@RequestMapping(path = "book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("{id}")
    public BookView get(@PathVariable long id) {
        return bookService.get(id);
    }

    @GetMapping("search")
    public List<BookView> search(@RequestParam(required = false) String q) {
        return bookService.search(q);
    }

    @GetMapping("most-popular")
    public List<BookView> mostPopular() {
        return bookService.getMostPopular();
    }


    @GetMapping("{id}/user-libraries")
    public List<UserLibrarySmallView> getUserLibraries(@PathVariable long id) {
        return bookService.getBookUserLibraries(id);
    }

    @GetMapping("suggested")
    public List<BookView> suggested() {
        return bookService.suggested();
    }


}
