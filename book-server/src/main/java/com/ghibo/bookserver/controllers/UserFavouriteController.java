package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserFavouriteView;
import com.ghibo.bookserver.services.UserFavouriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Favourite")
@RestController
@RequestMapping(path = "user-favourite")
@RequiredArgsConstructor
public class UserFavouriteController {

    private final UserFavouriteService userFavouriteService;

    @PostMapping("{idBook}/add")
    public UserFavouriteView add(@PathVariable long idBook) {
        return userFavouriteService.add(idBook);
    }

    @DeleteMapping("{idBook}/remove")
    public void remove(@PathVariable long idBook) {
        userFavouriteService.remove(idBook);
    }

    @GetMapping("books")
    public List<BookView> getBooks(Authentication authentication) {
        return userFavouriteService.getFavouritesBook(((UserDetailsImpl) authentication.getPrincipal()).getId());
    }


}
