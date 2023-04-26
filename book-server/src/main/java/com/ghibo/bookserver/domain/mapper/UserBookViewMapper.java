package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserBookView;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.UserBook;
import org.mapstruct.Mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBookViewMapper {


    UserBookView toUserBookView(UserBook userBook);

    List<UserBookView> toUserBookView(List<UserBook> userBooks);

    default Date toStartDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    default Date toCompletedDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
