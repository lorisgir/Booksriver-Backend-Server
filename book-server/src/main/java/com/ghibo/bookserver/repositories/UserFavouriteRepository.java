package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.UserFavourite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavouriteRepository extends CrudRepository<UserFavourite, Long> {

    default UserFavourite getById(long id) {
        Optional<UserFavourite> optionalBook = findById(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    default UserFavourite getByBookAndUserId(long bookId, long userId) {
        Optional<UserFavourite> optionalBook = findByBookIdAndUserId(bookId, userId);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    Optional<UserFavourite> findByBookIdAndUserId(long bookId, long userId);

    List<UserFavourite> findByUserId(long userId);
}
