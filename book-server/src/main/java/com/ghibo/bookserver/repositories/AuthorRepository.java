package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.domain.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    default Author getById(long id) {
        Optional<Author> optionalAuthor = findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalAuthor.get();
    }


    Optional<Author> findByNameIgnoreCase(String query);

    List<Author> findTop100ByNameContainingIgnoreCase(String query);

    @Query(value = "SELECT * FROM authors OFFSET floor(random() * (SELECT COUNT(*) FROM authors)) LIMIT 20;", nativeQuery = true)
    List<Author> fetchRandom();
}
