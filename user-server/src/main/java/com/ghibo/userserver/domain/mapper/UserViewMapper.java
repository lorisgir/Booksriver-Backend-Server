package com.ghibo.userserver.domain.mapper;

import com.ghibo.userserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserViewMapper {
    UserView toUserView(User user);
    UserView toUserView(UserDetailsImpl user);

    List<UserView> toUserView(List<User> users);
}
