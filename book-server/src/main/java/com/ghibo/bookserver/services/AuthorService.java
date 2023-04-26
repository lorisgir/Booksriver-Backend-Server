package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.AuthorView;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.mapper.AuthorViewMapper;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.repositories.AuthorRepository;
import com.ghibo.bookserver.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepo;
    private final BookRepository bookRepository;

    AuthorViewMapper authorViewMapper = Mappers.getMapper(AuthorViewMapper.class);
    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);

    @Autowired
    SessionParam sessionParam;

    public List<AuthorView> search(String q) {
        List<Author> categories = authorRepo.findTop100ByNameContainingIgnoreCase(q);
        return authorViewMapper.toAuthorView(categories);
    }

    public List<AuthorView> getMostPopular() {
        List<Author> categories = authorRepo.fetchRandom();
        return authorViewMapper.toAuthorView(categories);
    }

    public List<BookView> getAuthorBooks(long id) {
        sessionParam.setUserFilter();

        Author author = authorRepo.getById(id);
        List<Book> books = bookRepository.findTop100ByAuthorsId(author.getId());
        return bookViewMapper.toBookView(books);
    }

    public AuthorView getAuthorById(long id) {
        Author author = authorRepo.getById(id);
        return authorViewMapper.toAuthorView(author);
    }

}
