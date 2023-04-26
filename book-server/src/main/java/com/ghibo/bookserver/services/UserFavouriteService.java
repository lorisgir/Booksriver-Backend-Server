package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.UserFavouriteView;
import com.ghibo.bookserver.domain.exceptions.BadRequestException;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.mapper.UserFavouriteViewMapper;
import com.ghibo.bookserver.domain.models.UserFavourite;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.UserFavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFavouriteService {

    private final UserFavouriteRepository userFavouriteRepo;
    private final BookRepository bookRepo;

    UserFavouriteViewMapper userFavouriteViewMapper = Mappers.getMapper(UserFavouriteViewMapper.class);
    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);

    @Autowired
    SessionParam sessionParam;


    public List<BookView> getFavouritesBook(long userId) {
        sessionParam.setUserFilter();

        List<UserFavourite> userFavourites = userFavouriteRepo.findByUserId(userId);
        return bookViewMapper.toBookView(userFavourites.stream().map(UserFavourite::getBook).collect(Collectors.toList()));
    }


    public UserFavouriteView add(long bookId) {
        if (userFavouriteRepo.findByBookIdAndUserId(bookId, sessionParam.getUid()).isPresent()) {
            throw new BadRequestException("Esiste gia!");
        }
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setUserId(sessionParam.getUid());
        userFavourite.setBook(bookRepo.getById(bookId));
        userFavouriteRepo.save(userFavourite);
        return userFavouriteViewMapper.toUserFavouriteView(userFavourite);
    }

    public void remove(long bookId) {
        UserFavourite userFavourite = userFavouriteRepo.getByBookAndUserId(bookId, sessionParam.getUid());
        userFavouriteRepo.delete(userFavourite);
    }


}
