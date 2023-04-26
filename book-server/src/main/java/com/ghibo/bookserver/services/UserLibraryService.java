package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserLibrarySmallView;
import com.ghibo.bookserver.domain.dto.UserLibraryView;
import com.ghibo.bookserver.domain.dto.requests.*;
import com.ghibo.bookserver.domain.exceptions.BadRequestException;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.mapper.UserLibraryViewMapper;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.UserLibrary;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.UserLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepo;
    private final BookRepository bookRepo;

    UserLibraryViewMapper userLibraryViewMapper = Mappers.getMapper(UserLibraryViewMapper.class);
    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);

    @Autowired
    SessionParam sessionParam;


    public UserLibrarySmallView get(long id) {
        sessionParam.setUserFilter();

        return userLibraryViewMapper.toUserLibrarySmallView(userLibraryRepo.getById(id));
    }

    public List<UserLibrarySmallView> getByUserId(long userId) {
        sessionParam.setUserFilter();

        return userLibraryViewMapper.toUserLibrarySmallView(userLibraryRepo.findByUserId(userId));
    }

    public List<UserLibrarySmallView> getByUserIdAndBookId(long userId, long bookId) {
        sessionParam.setUserFilter();

        return userLibraryViewMapper.toUserLibrarySmallView(userLibraryRepo.findByUserIdAndBooksId(userId, bookId));
    }

    public List<UserLibrarySmallView> search(String name) {
        sessionParam.setUserFilter();

        return userLibraryViewMapper.toUserLibrarySmallView(userLibraryRepo.findTop100ByNameContainingIgnoreCase(name));
    }


    public List<BookView> getBooksByLibraryId(long id) {
        sessionParam.setUserFilter();

        UserLibrary userLibrary = userLibraryRepo.getById(id);
        return bookViewMapper.toBookView(new ArrayList<>(userLibrary.getBooks()));
    }


    public UserLibraryView create(CreateLibraryRequest createLibraryRequest) {
        sessionParam.setUserFilter();

        UserLibrary userLibrary = new UserLibrary(sessionParam.getUid(), createLibraryRequest.getName());
        return userLibraryViewMapper.toUserLibraryView(userLibraryRepo.save(userLibrary));
    }

    public UserLibraryView edit(EditLibraryRequest editLibraryRequest, long id) {
        sessionParam.setUserFilter();

        UserLibrary userLibrary = userLibraryRepo.getById(id);
        userLibraryViewMapper.update(editLibraryRequest, userLibrary);
        return userLibraryViewMapper.toUserLibraryView(userLibraryRepo.save(userLibrary));
    }

    public void delete(long id) {
        UserLibrary userLibrary = userLibraryRepo.getById(id);
        userLibraryRepo.delete(userLibrary);
    }

    public UserLibraryView addBook(AddBookToLibraryRequest addBookToLibraryRequest, long id) {
        sessionParam.setUserFilter();

        UserLibrary userLibrary = userLibraryRepo.getById(id);
        Book book = bookRepo.getById(addBookToLibraryRequest.getBookId());

        if (userLibraryRepo.findByIdAndBooksId(id, book.getId()).isPresent()) {
            throw new BadRequestException("C'e' gia!");
        }

        userLibrary.getBooks().add(book);
        return userLibraryViewMapper.toUserLibraryView(userLibraryRepo.save(userLibrary));
    }

    public List<UserLibrarySmallView> addsBook(AddBookToLibrariesRequest addBookToLibraryRequest) {
        sessionParam.setUserFilter();

        userLibraryRepo.deleteByBooksIdAndUserId(addBookToLibraryRequest.getBookId(), sessionParam.getUid());

        List<UserLibrary> bookUserLibraries = new ArrayList<>();
        for (long id : addBookToLibraryRequest.getLibrariesId()) {
            UserLibrary userLibrary = userLibraryRepo.getById(id);
            Book book = bookRepo.getById(addBookToLibraryRequest.getBookId());

            if (userLibraryRepo.findByIdAndBooksId(id, book.getId()).isEmpty()) {
                userLibrary.getBooks().add(book);
                userLibraryRepo.save(userLibrary);
            }

            bookUserLibraries.add(userLibrary);
        }

        return userLibraryViewMapper.toUserLibrarySmallView(bookUserLibraries);
    }

    public UserLibraryView removeBook(RemoveBookToLibraryRequest removeBookToLibraryRequest, long id) {
        sessionParam.setUserFilter();

        UserLibrary userLibrary = userLibraryRepo.getById(id);
        Book book = bookRepo.getById(removeBookToLibraryRequest.getBookId());
        userLibrary.getBooks().remove(book);
        return userLibraryViewMapper.toUserLibraryView(userLibraryRepo.save(userLibrary));
    }


}
