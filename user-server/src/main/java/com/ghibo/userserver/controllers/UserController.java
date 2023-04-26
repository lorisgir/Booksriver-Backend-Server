package com.ghibo.userserver.controllers;


import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.dto.UserViewComplete;
import com.ghibo.userserver.domain.dto.requests.UpdateUserRequest;
import com.ghibo.userserver.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping(path = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("search")
    public List<UserView> search(@RequestParam(required = false) String q) {
        return userService.searchUsers(q);
    }

    @GetMapping("me")
    public UserViewComplete me() {
        return userService.me();
    }

    @GetMapping("meSmall")
    public UserView meSmall() {
        return userService.meSmall();
    }

    @PostMapping("me/update")
    public UserView update(@RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @GetMapping("{userId}")
    public UserViewComplete get(@PathVariable long userId) {
        return userService.get(userId);
    }
}
