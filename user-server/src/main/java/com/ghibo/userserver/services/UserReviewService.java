package com.ghibo.userserver.services;

import com.ghibo.userserver.configuration.MQConfig;
import com.ghibo.userserver.configuration.SessionParam;
import com.ghibo.userserver.configuration.feign.BookFeignClient;
import com.ghibo.userserver.domain.dto.RabbitMqWrapper;
import com.ghibo.userserver.domain.dto.UserReviewView;
import com.ghibo.userserver.domain.dto.requests.CreateReviewRequest;
import com.ghibo.userserver.domain.dto.requests.UpdateReviewRequest;
import com.ghibo.userserver.domain.exceptions.BadRequestException;
import com.ghibo.userserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.userserver.domain.mapper.UserReviewMapper;
import com.ghibo.userserver.domain.dto.BookView;
import com.ghibo.userserver.domain.models.User;
import com.ghibo.userserver.domain.models.UserReview;
import com.ghibo.userserver.repositories.UserRepository;
import com.ghibo.userserver.repositories.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReviewService {

    @Autowired
    SessionParam sessionParam;

    private final UserReviewRepository userReviewRepo;
    private final UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BookFeignClient bookFeignClient;

    UserReviewMapper userReviewMapper = Mappers.getMapper(UserReviewMapper.class);

    @Transactional
    public UserReviewView create(CreateReviewRequest request) {
        User user = userRepository.getById(sessionParam.getUid());
        BookView bookView = bookFeignClient.getBook(request.getBookId());

        if (userReviewRepo.findByUserIdAndBookId(user.getId(), request.getBookId()).isPresent()) {
            throw new BadRequestException("Review already exists for this user and book");
        }
        UserReview userReview = userReviewMapper.create(request);
        userReview.setUser(user);
        userReview = userReviewRepo.save(userReview);

        sendRabbitMqMessage("create", userReview, null);

        return userReviewMapper.toUserReviewView(userReview);
    }


    @Transactional
    public UserReviewView update(long id, UpdateReviewRequest request) {
        UserReview userReview = userReviewRepo.getById(id);
        Double oldVal = userReview.getScore();
        userReviewMapper.update(request, userReview);
        userReview = userReviewRepo.save(userReview);
        sendRabbitMqMessage("update", userReview, oldVal);
        return userReviewMapper.toUserReviewView(userReview);
    }

    @Transactional
    public void delete(long id) {
        UserReview userReview = userReviewRepo.getById(id);
        userReviewRepo.delete(userReview);
        sendRabbitMqMessage("delete", userReview, null);
    }

    @Transactional
    public UserReviewView getByBookIdAndUserId(long bookId, long userId) {
        Optional<UserReview> optionalUserReview = userReviewRepo.findByUserIdAndBookId(userId, bookId);
        if (optionalUserReview.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return userReviewMapper.toUserReviewView(optionalUserReview.get());
    }

    private void sendRabbitMqMessage(String type, UserReview userReview, Object extraData) {
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, new RabbitMqWrapper<>("review", type, userReviewMapper.toUserReviewView(userReview), extraData));
    }


    public List<UserReviewView> getBookUserReviews(long id) {
        List<UserReview> userReviews = userReviewRepo.findTop100ByBookId(id);
        return userReviewMapper.toUserReviewView(userReviews);
    }


}
