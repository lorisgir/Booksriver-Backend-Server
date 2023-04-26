package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.UserBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends CrudRepository<UserBook, Long> {

    default UserBook getById(long id) {
        Optional<UserBook> optionalBook = findById(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    default UserBook getByBookAndUserId(long bookId, long userId) {
        Optional<UserBook> optionalBook = findByBookIdAndUserId(bookId, userId);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    Optional<UserBook> findByBookIdAndUserId(long bookId, long userId);

    List<UserBook> findByUserIdAndStatus(long userId, int status);
    List<UserBook> findByUserId(long userId);
}
