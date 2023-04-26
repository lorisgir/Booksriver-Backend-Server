package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.models.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookViewMapper {


    BookView toBookView(Book book);

    List<BookView> toBookView(List<Book> books);


    @AfterMapping
    default void setMissingField(Book book, @MappingTarget BookView bookView) {
        if (book.getUserBookList().size() > 0) {
            bookView.setUserBook(Mappers.getMapper(UserBookViewMapper.class).toUserBookView(book.getUserBookList().get(0)));
        }
        bookView.setFavourite(book.getUserFavouriteList().size() > 0);
    }
}
