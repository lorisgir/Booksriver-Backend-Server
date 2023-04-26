package com.ghibo.userserver.controllers;


import com.ghibo.userserver.domain.dto.UserFollowView;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.services.UserFollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Follow")
@RestController
@RequestMapping(path = "user-follow")
@RequiredArgsConstructor
public class UserFollowController {

    private final UserFollowService userFollowService;


    @PostMapping("{userId}/follow")
    public UserFollowView follow(@PathVariable long userId) {
        return userFollowService.follow(userId);
    }

    @PostMapping("{userId}/unfollow")
    public void unfollow(@PathVariable long userId) {
        userFollowService.unfollow(userId);
    }

    @GetMapping("{userId}/following")
    public List<UserView> following(@PathVariable long userId) {
        return userFollowService.following(userId);
    }

    @GetMapping("{userId}/followers")
    public List<UserView> followers(@PathVariable long userId) {
        return  userFollowService.followers(userId);
    }
}
