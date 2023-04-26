package com.ghibo.userserver.services;

import com.ghibo.userserver.configuration.SessionParam;
import com.ghibo.userserver.configuration.feign.BookFeignClient;
import com.ghibo.userserver.domain.dto.UserLibraryView;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.dto.UserViewComplete;
import com.ghibo.userserver.domain.dto.requests.CreateUserRequest;
import com.ghibo.userserver.domain.dto.requests.LoginSocialRequest;
import com.ghibo.userserver.domain.dto.requests.UpdateUserRequest;
import com.ghibo.userserver.domain.exceptions.BadRequestException;
import com.ghibo.userserver.domain.mapper.UserEditMapper;
import com.ghibo.userserver.domain.mapper.UserViewCompleteMapper;
import com.ghibo.userserver.domain.mapper.UserViewMapper;
import com.ghibo.userserver.domain.models.User;
import com.ghibo.userserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    SessionParam sessionParam;

    @Autowired
    BookFeignClient bookFeignClient;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    UserEditMapper userEditMapper = Mappers.getMapper(UserEditMapper.class);
    UserViewMapper userViewMapper = Mappers.getMapper(UserViewMapper.class);
    UserViewCompleteMapper userViewCompleteMapper = Mappers.getMapper(UserViewCompleteMapper.class);

    @Transactional
    public UserView create(CreateUserRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists!");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new BadRequestException("Passwords don't match!");
        }

        User user = userEditMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepo.save(user);

        return userViewMapper.toUserView(user);
    }

    @Transactional
    public UserView loginSocial(LoginSocialRequest request) {
        Optional<User> optionalUser = userRepo.findByEmailOrGoogleId(request.getEmail(), request.getId());

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.getGoogleId() == null || user.getGoogleId().isEmpty()) {
                user.setGoogleId(request.getId());
                userRepo.save(user);
            }
        } else {
            user = new User();
            user.setEmail(request.getEmail());
            user.setGoogleId(request.getId());
            String username = request.getEmail().split("@")[0];
            user.setUsername(username);
            user = userRepo.save(user);
        }


        return userViewMapper.toUserView(user);
    }

    @Transactional
    public UserView update(UpdateUserRequest request) {
        User user = userRepo.getById(sessionParam.getUid());
        /*if (request.getPassword() != null && !request.getPassword().isBlank()) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }*/
        userEditMapper.update(request, user);
        user = userRepo.save(user);
        return userViewMapper.toUserView(user);

    }

    public UserView meSmall() {
        return userViewMapper.toUserView(userRepo.getById(sessionParam.getUid()));
    }

    public UserViewComplete me() {
        sessionParam.setUserFilter();

        UserViewComplete userViewComplete = userViewCompleteMapper.toUserViewComplete(userRepo.getById(sessionParam.getUid()));
        userViewComplete.setUserLibraryViews(getUserLibraries(sessionParam.getUid()));
        return userViewComplete;
    }


    public UserViewComplete get(long id) {
        sessionParam.setUserFilter(id);

        User user = userRepo.getById(id);
        UserViewComplete userViewComplete = userViewCompleteMapper.toUserViewComplete(userRepo.getById(id));
        userViewComplete.setUserLibraryViews(getUserLibraries(id));
        userViewComplete.setFollowed(user.getUserFollowed().stream().anyMatch(el -> el.getUser().getId() == sessionParam.getUid()));

        return userViewComplete;
    }


    public List<UserView> searchUsers(String query) {
        List<User> users = userRepo.findTop100ByUsernameContainingIgnoreCase(query);
        return userViewMapper.toUserView(users);
    }

    public List<UserLibraryView> getUserLibraries(long userId) {
        return bookFeignClient.getUserLibraries(userId);
    }

}
