package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.*;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.mapper.UserLibraryViewMapper;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.UserBook;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.UserBookRepository;
import com.ghibo.bookserver.repositories.UserLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepo;
    private final UserLibraryRepository userLibraryRepo;
    private final UserBookRepository userBookRepository;

    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);
    UserLibraryViewMapper userLibraryViewMapper = Mappers.getMapper(UserLibraryViewMapper.class);

    @Autowired
    SessionParam sessionParam;

    public BookView get(long id) {
        sessionParam.setUserFilter();

        return bookViewMapper.toBookView(bookRepo.getById(id));
    }

    public List<BookView> search(String q) {
        sessionParam.setUserFilter();

        List<Book> books = bookRepo.findTop100ByTitleContainingIgnoreCase(q);
        return bookViewMapper.toBookView(books);
    }

    public List<BookView> getMostPopular() {
        sessionParam.setUserFilter();

        List<Book> books = bookRepo.fetchRandom();
        return bookViewMapper.toBookView(books);
    }

    public List<BookView> suggested() {
        sessionParam.setUserFilter();

        List<QueryByTop> queryByTopAuthors = bookRepo.orderByAuthor(sessionParam.getUid());
        List<QueryByTop> queryByTopCategories = bookRepo.orderByCategories(sessionParam.getUid());

        List<Book> books = bookRepo.findDistinctTop100ByAuthorsIdInOrCategoriesIdIn(queryByTopAuthors.stream().map(QueryByTop::getId).collect(Collectors.toList()), queryByTopCategories.stream().map(QueryByTop::getId).collect(Collectors.toList()));

        return bookViewMapper.toBookView(books);
    }


    public List<UserLibrarySmallView> getBookUserLibraries(long bookId) {
        sessionParam.setUserFilter();

        return userLibraryViewMapper.toUserLibrarySmallView(userLibraryRepo.findByBooksIdAndUserId(bookId, sessionParam.getUid()));
    }


    public void syncReview(RabbitMqWrapper<UserReviewView> wrapper) {
        System.out.println("syncing...");
        Optional<Book> optionalBook = bookRepo.findById(wrapper.getData().getBookId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Double sum = book.getAverageRating() * book.getRatingsCount();

            if (wrapper.getType().equals("delete")) {
                sum -= wrapper.getData().getScore();
                book.setRatingsCount(book.getRatingsCount() - 1);
            } else if (wrapper.getType().equals("create")) {
                sum += wrapper.getData().getScore();
                book.setRatingsCount(book.getRatingsCount() + 1);
            } else if (wrapper.getType().equals("update")) {
                sum -= ((Double) wrapper.getExtraData());
                sum += wrapper.getData().getScore();
            }

            if (book.getRatingsCount() == 0) {
                book.setAverageRating(0.0);
            } else {
                book.setAverageRating(sum / book.getRatingsCount());
            }

            bookRepo.save(book);
        }

    }

}
