package com.ghibo.userserver.repositories;

import com.ghibo.userserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.userserver.domain.models.UserReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReviewRepository extends CrudRepository<UserReview, Long> {

    default UserReview getById(long id) {
        Optional<UserReview> optionalUserReview = findById(id);
        if (optionalUserReview.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalUserReview.get();
    }

    List<UserReview> findTop100ByBookId(long bookId);

    Optional<UserReview> findByUserIdAndBookId(long userId, long bookId);

}
