package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserLibrarySmallView;
import com.ghibo.bookserver.domain.dto.UserLibraryView;
import com.ghibo.bookserver.domain.dto.requests.*;
import com.ghibo.bookserver.services.UserLibraryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User Library")
@RestController
@RequestMapping(path = "user-library")
@RequiredArgsConstructor
public class UserLibraryController {

    private final UserLibraryService userLibraryService;

    @GetMapping("{id}")
    public UserLibrarySmallView get(@PathVariable long id) {
        return userLibraryService.get(id);
    }

    @GetMapping("{id}/books")
    public List<BookView> getBooksByLibrary(@PathVariable long id) {
        return userLibraryService.getBooksByLibraryId(id);
    }

    @GetMapping("search")
    public List<UserLibrarySmallView> search(@RequestParam(required = false) String q) {
        return userLibraryService.search(q);
    }


    @GetMapping("user/{id}/libraries")
    public List<UserLibrarySmallView> getLibrariesByUser(@PathVariable long id) {
        return userLibraryService.getByUserId(id);
    }

    @GetMapping("user/{userId}/book/{bookId}/libraries")
    public List<UserLibrarySmallView> getLibrariesByBook(@PathVariable long userId, @PathVariable long bookId) {
        return userLibraryService.getByUserIdAndBookId(userId, bookId);
    }


    @PostMapping("create")
    public UserLibraryView create(@RequestBody @Valid CreateLibraryRequest createLibraryRequest) {
        return userLibraryService.create(createLibraryRequest);
    }

    @PostMapping("{id}/edit")
    public UserLibraryView edit(@PathVariable long id, @RequestBody @Valid EditLibraryRequest editLibraryRequest) {
        return userLibraryService.edit(editLibraryRequest, id);
    }

    @DeleteMapping("{id}/delete")
    public void delete(@PathVariable long id) {
        userLibraryService.delete(id);
    }

    @PostMapping("{id}/add-book")
    public UserLibraryView addBook(@PathVariable long id, @RequestBody @Valid AddBookToLibraryRequest addBookToLibraryRequest) {
        return userLibraryService.addBook(addBookToLibraryRequest, id);
    }

    @PostMapping("adds-book")
    public List<UserLibrarySmallView>  addsBook(@RequestBody @Valid AddBookToLibrariesRequest addBookToLibrariesRequest) {
        return userLibraryService.addsBook(addBookToLibrariesRequest);
    }

    @PostMapping("{id}/remove-book")
    public UserLibraryView removeBook(@PathVariable long id, @RequestBody @Valid RemoveBookToLibraryRequest removeBookToLibraryRequest) {
        return userLibraryService.removeBook(removeBookToLibraryRequest, id);
    }


}
