package com.ghibo.userserver.services;

import com.ghibo.userserver.configuration.SessionParam;
import com.ghibo.userserver.domain.dto.UserFollowView;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.exceptions.BadRequestException;
import com.ghibo.userserver.domain.mapper.UserEditMapper;
import com.ghibo.userserver.domain.mapper.UserFollowMapper;
import com.ghibo.userserver.domain.mapper.UserViewMapper;
import com.ghibo.userserver.domain.models.UserFollow;
import com.ghibo.userserver.repositories.UserFollowRepository;
import com.ghibo.userserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFollowService {

    @Autowired
    SessionParam sessionParam;

    private final UserFollowRepository userFollowRepo;
    private final UserRepository userRepo;

    UserEditMapper userEditMapper = Mappers.getMapper(UserEditMapper.class);
    UserFollowMapper userFollowMapper = Mappers.getMapper(UserFollowMapper.class);
    UserViewMapper userViewMapper = Mappers.getMapper(UserViewMapper.class);

    @Transactional
    public UserFollowView follow(long userId) {
        if (userId == sessionParam.getUid()) {
            throw new BadRequestException("You can follow yourself!");
        }
        Optional<UserFollow> userFollowOptional = userFollowRepo.findByUserIdAndUserFollowedId(sessionParam.getUid(), userId);
        if (userFollowOptional.isPresent()) {
            throw new BadRequestException("Already exists!");
        }
        UserFollow userFollow = new UserFollow(userRepo.getById(sessionParam.getUid()), userRepo.getById(userId));
        userFollowRepo.save(userFollow);

        return userFollowMapper.toUserFollowView(userFollow);
    }

    @Transactional
    public void unfollow(long userId) {
        Optional<UserFollow> userFollowOptional = userFollowRepo.findByUserIdAndUserFollowedId(sessionParam.getUid(), userId);
        if (userFollowOptional.isEmpty()) {
            throw new BadRequestException("Does not exists!");
        }
        userFollowRepo.delete(userFollowOptional.get());
    }

    @Transactional
    public List<UserView> following(long userId) {
        List<UserFollow> userFollowList = userFollowRepo.findByUserId(userId);
        return userViewMapper.toUserView(userFollowList.stream().map(UserFollow::getUserFollowed).collect(Collectors.toList()));
    }

    @Transactional
    public List<UserView> followers(long userId) {
        List<UserFollow> userFollowList = userFollowRepo.findByUserFollowedId(userId);
        return userViewMapper.toUserView(userFollowList.stream().map(UserFollow::getUser).collect(Collectors.toList()));
    }

}
