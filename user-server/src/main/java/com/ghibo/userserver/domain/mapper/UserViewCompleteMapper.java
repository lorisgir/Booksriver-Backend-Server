package com.ghibo.userserver.domain.mapper;

import com.ghibo.userserver.configuration.feign.BookFeignClient;
import com.ghibo.userserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.userserver.domain.dto.UserViewComplete;
import com.ghibo.userserver.domain.models.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserViewCompleteMapper {
    UserViewComplete toUserViewComplete(User user);

    UserViewComplete toUserViewComplete(UserDetailsImpl user);

    List<UserViewComplete> toUserViewComplete(List<User> users);

    @AfterMapping
    default void setMissingFields(User user, @MappingTarget UserViewComplete userViewComplete) {
        userViewComplete.setUserFollowsCount(user.getUserFollows().size());
        userViewComplete.setUserFollowedCount(user.getUserFollowed().size());
    }

}
