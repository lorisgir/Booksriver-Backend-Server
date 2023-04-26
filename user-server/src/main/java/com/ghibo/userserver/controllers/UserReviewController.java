package com.ghibo.userserver.controllers;


import com.ghibo.userserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.userserver.domain.dto.UserReviewView;
import com.ghibo.userserver.domain.dto.requests.CreateReviewRequest;
import com.ghibo.userserver.domain.dto.requests.UpdateReviewRequest;
import com.ghibo.userserver.services.UserReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Review")
@RestController
@RequestMapping(path = "review")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("book/{bookId}/user/{userId}")
    public UserReviewView getByBookIdAndUserId(@PathVariable long bookId, @PathVariable long userId) {
        return userReviewService.getByBookIdAndUserId(bookId, userId);
    }

    @PostMapping("create")
    public UserReviewView create(@RequestBody CreateReviewRequest request) {
        return userReviewService.create(request);
    }

    @PostMapping("{id}/update")
    public UserReviewView update(@PathVariable long id, @RequestBody UpdateReviewRequest request) {
        return userReviewService.update(id, request);
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userReviewService.delete(id);
    }

    @GetMapping("book/{bookId}/list")
    public List<UserReviewView> list(@PathVariable long bookId) {
        return userReviewService.getBookUserReviews(bookId);
    }


}
