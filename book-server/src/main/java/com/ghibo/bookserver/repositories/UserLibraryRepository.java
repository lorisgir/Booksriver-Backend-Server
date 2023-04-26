package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.UserLibrary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLibraryRepository extends CrudRepository<UserLibrary, Long> {

    default UserLibrary getById(long id) {
        Optional<UserLibrary> optionalBook = findById(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    List<UserLibrary> findByBooksIdAndUserId(long bookId, long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_library_books WHERE books_id = :bookId and user_libraries_id IN (SELECT id FROM user_libraries WHERE user_id = :userId)", nativeQuery = true)
    void deleteByBooksIdAndUserId(long bookId, long userId);

    List<UserLibrary> findByUserIdAndBooksId(long userId, long bookId);

    Optional<UserLibrary> findByIdAndBooksId(long id, long bookId);

    List<UserLibrary> findByUserId(long userId);

    List<UserLibrary> findTop100ByNameContainingIgnoreCase(String name);


}
