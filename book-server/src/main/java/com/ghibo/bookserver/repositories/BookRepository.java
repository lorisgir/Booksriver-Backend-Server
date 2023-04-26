package com.ghibo.bookserver.repositories;

import com.ghibo.bookserver.domain.dto.QueryByTop;
import com.ghibo.bookserver.domain.exceptions.ResourceNotFoundException;
import com.ghibo.bookserver.domain.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    default Book getById(long id) {
        Optional<Book> optionalBook = findById(id);
        if (optionalBook.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return optionalBook.get();
    }

    Optional<Book> findByTitleIgnoreCase(String query);

    List<Book> findTop100ByTitleContainingIgnoreCase(String query);

    List<Book> findTop100ByAuthorsId(long id);

    List<Book> findTop100ByCategoriesId(long id);

    List<Book> findDistinctTop100ByAuthorsIdInOrCategoriesIdIn(List<Long> authorsId, List<Long> categoriesId);

    @Query(value = "SELECT * FROM books OFFSET floor(random() * (SELECT COUNT(*) FROM books)) LIMIT 50;", nativeQuery = true)
    List<Book> fetchRandom();

    @Query(value = "SELECT book_authors.authors_id as id, COUNT(*) as c FROM books, user_book, book_authors WHERE user_id = :userId and user_book.book_id = books.id and user_book.book_id = book_authors.books_id GROUP BY authors_id ORDER BY c DESC", nativeQuery = true)
    List<QueryByTop> orderByAuthor(long userId);

    @Query(value = "SELECT book_categories.categories_id as id, COUNT(*) as c FROM books, user_book, book_categories WHERE user_id = :userId and user_book.book_id = books.id and user_book.book_id = book_categories.books_id GROUP BY categories_id ORDER BY c DESC", nativeQuery = true)
    List<QueryByTop> orderByCategories(long userId);

}
