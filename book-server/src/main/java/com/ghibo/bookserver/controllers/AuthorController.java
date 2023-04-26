package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.bookserver.domain.dto.AuthorView;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.services.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Author")
@RestController
@RequestMapping(path = "author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("search")
    public List<AuthorView> search(@RequestParam(required = false) String q, Authentication authentication) {
        return authorService.search(q);
    }

    @GetMapping("most-popular")
    public List<AuthorView> mostPopular() {
        return authorService.getMostPopular();
    }

    @GetMapping("{id}/books")
    public List<BookView> authorBooks(@PathVariable long id) {
        return authorService.getAuthorBooks(id);
    }

    @GetMapping("{id}")
    public AuthorView get(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }
}
