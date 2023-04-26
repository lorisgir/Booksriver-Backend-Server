package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    default Category getById(long id) {
        Optional<Category> optionalCategory = findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalCategory.get();
    }


    Optional<Category> findByNameIgnoreCase(String query);

    List<Category> findTop100ByNameContainingIgnoreCase(String query);

    @Query(value = "SELECT * FROM categories OFFSET floor(random() * (SELECT COUNT(*) FROM categories)) LIMIT 20;", nativeQuery = true)
    List<Category> fetchRandom();
}
