package com.ghibo.userserver.domain.mapper;

import com.ghibo.userserver.domain.dto.UserReviewView;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.dto.requests.CreateReviewRequest;
import com.ghibo.userserver.domain.dto.requests.UpdateReviewRequest;
import com.ghibo.userserver.domain.models.User;
import com.ghibo.userserver.domain.models.UserReview;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {


    UserReviewView toUserReviewView(UserReview userReview);

    List<UserReviewView> toUserReviewView(List<UserReview> userReviews);

    UserReview toUserReview(UserReviewView userReviewView);

    List<UserReview> toUserReview(List<UserReviewView> userReviewViews);

    UserReview create(CreateReviewRequest createReviewRequest);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(UpdateReviewRequest request, @MappingTarget UserReview userReview);

    UserView toUserView(User user);

    User toUser(UserView userView);

}
