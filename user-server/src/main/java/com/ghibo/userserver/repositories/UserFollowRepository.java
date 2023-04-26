package com.ghibo.userserver.repositories;

import com.ghibo.userserver.domain.models.UserFollow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowRepository extends CrudRepository<UserFollow, Long> {
    List<UserFollow> findByUserId(long userId);

    List<UserFollow> findByUserFollowedId(long userId);

    Optional<UserFollow> findByUserIdAndUserFollowedId(long userId, long userFollowedId);
}
