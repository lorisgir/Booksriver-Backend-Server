package com.ghibo.userserver.domain.dto;

import lombok.Data;


@Data
public class UserFollowView {
    private long id;

    private UserView user;

    private UserView userFollowed;


}
