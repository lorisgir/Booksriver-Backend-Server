package com.ghibo.userserver.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserViewComplete {
    private long id;
    private String username;
    private String email;

    private int userFollowsCount;
    private int userFollowedCount;
    private List<UserLibraryView> userLibraryViews;

    private boolean followed;
}
