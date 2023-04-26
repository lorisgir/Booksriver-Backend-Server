package com.ghibo.userserver.domain.mapper;

import com.ghibo.userserver.domain.dto.UserFollowView;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.models.User;
import com.ghibo.userserver.domain.models.UserFollow;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFollowMapper {


    UserFollowView toUserFollowView(UserFollow userFollow);

    List<UserFollowView> toUserFollowView(List<UserFollow> userFollows);

    UserView toUserView(User user);

    User toUser(UserView userView);

}
