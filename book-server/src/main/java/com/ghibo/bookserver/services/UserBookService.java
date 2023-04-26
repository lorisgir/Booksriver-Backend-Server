package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserBookView;
import com.ghibo.bookserver.domain.dto.UserStatusLibraryView;
import com.ghibo.bookserver.domain.dto.requests.ChangeStatusUserBookRequest;
import com.ghibo.bookserver.domain.dto.requests.ReadBookRequest;
import com.ghibo.bookserver.domain.exceptions.BadRequestException;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.mapper.UserBookViewMapper;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.UserBook;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookRepository userBookRepo;
    private final BookRepository bookRepo;

    UserBookViewMapper userBookViewMapper = Mappers.getMapper(UserBookViewMapper.class);
    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);

    @Autowired
    SessionParam sessionParam;


    public UserBookView get(long id) {
        return userBookViewMapper.toUserBookView(userBookRepo.getById(id));
    }

    public List<UserStatusLibraryView> getStatusLibrariesByUser(long id) {
        sessionParam.setUserFilter();

        List<UserStatusLibraryView> userStatusLibraryViews = new ArrayList<>();

        String[] labels = {"Not Yet Started", "Currently Reading", "Completed", "Dropped"};
        for (int i = 0; i < 4; i++) {
            List<UserBook> userBooks = userBookRepo.findByUserIdAndStatus(id, i);
            userStatusLibraryViews.add(new UserStatusLibraryView(i, labels[i], bookViewMapper.toBookView(userBooks.stream().map(UserBook::getBook).collect(Collectors.toList()))));
        }


        return userStatusLibraryViews;
    }

    public UserBookView add(long idBook) {
        Optional<UserBook> optionalUserBook = userBookRepo.findByBookIdAndUserId(idBook, sessionParam.getUid());
        if (optionalUserBook.isPresent()) {
            UserBook userBook = optionalUserBook.get();
            userBook.setStatus(0);
            userBook.setPagesRead(0);
            userBook.setStartDate(new Date());
            userBookRepo.save(userBook);
            return userBookViewMapper.toUserBookView(userBook);
        } else {
            UserBook userBook = new UserBook();
            userBook.setUserId(sessionParam.getUid());
            userBook.setBook(bookRepo.getById(idBook));
            userBookRepo.save(userBook);
            return userBookViewMapper.toUserBookView(userBook);
        }

    }

    public UserBookView addReading(long idBook) {
        Optional<UserBook> optionalUserBook = userBookRepo.findByBookIdAndUserId(idBook, sessionParam.getUid());
        if (optionalUserBook.isPresent()) {
            UserBook userBook = optionalUserBook.get();
            userBook.setStatus(1);
            userBook.setPagesRead(0);
            userBook.setStartDate(new Date());
            userBookRepo.save(userBook);
            return userBookViewMapper.toUserBookView(userBook);
        } else {
            UserBook userBook = new UserBook(1);
            userBook.setUserId(sessionParam.getUid());
            userBook.setBook(bookRepo.getById(idBook));
            userBookRepo.save(userBook);
            return userBookViewMapper.toUserBookView(userBook);
        }
    }

    public void remove(long idBook) {
        UserBook userBook = userBookRepo.getByBookAndUserId(idBook, sessionParam.getUid());
        userBookRepo.delete(userBook);
    }

    public UserBookView changePagesRead(long idBook, ReadBookRequest readBookRequest) {
        Book book = bookRepo.getById(idBook);
        UserBook userBook = userBookRepo.getByBookAndUserId(idBook, sessionParam.getUid());

        if (readBookRequest.getPagesRead() <= 0) {
            userBook.setPagesRead(0);
            userBook.setStatus(0);
            userBook.setStartDate(null);
            userBook.setCompletedDate(null);
        } else if (readBookRequest.getPagesRead() >= book.getPageCount()) {
            userBook.setPagesRead(book.getPageCount());
            userBook.setStatus(2);
            userBook.setCompletedDate(new Date());
        } else {
            userBook.setPagesRead(readBookRequest.getPagesRead());
            userBook.setStatus(1);
        }
        userBookRepo.save(userBook);
        return userBookViewMapper.toUserBookView(userBook);
    }

    public UserBookView changeStatus(long idBook, ChangeStatusUserBookRequest changeStatusUserBookRequest) {
        UserBook userBook = userBookRepo.getByBookAndUserId(idBook, sessionParam.getUid());
        userBook.setStatus(changeStatusUserBookRequest.getStatus());
        if (changeStatusUserBookRequest.getStatus() == 0) {
            userBook.setPagesRead(0);
            userBook.setStartDate(null);
            userBook.setCompletedDate(null);
        } else if (changeStatusUserBookRequest.getStatus() == 2) {
            userBook.setPagesRead(userBook.getBook().getPageCount());
            userBook.setCompletedDate(new Date());
        }

        userBookRepo.save(userBook);
        return userBookViewMapper.toUserBookView(userBook);
    }

    public List<BookView> getBooksByStatus(int status) {
        sessionParam.setUserFilter();

        List<UserBook> userBooks = userBookRepo.findByUserIdAndStatus(sessionParam.getUid(), status);
        return bookViewMapper.toBookView(userBooks.stream().map(UserBook::getBook).collect(Collectors.toList()));
    }

    public List<BookView> getBooks() {
        sessionParam.setUserFilter();

        List<UserBook> userBooks = userBookRepo.findByUserId(sessionParam.getUid());
        return bookViewMapper.toBookView(userBooks.stream().map(UserBook::getBook).collect(Collectors.toList()));
    }


}
