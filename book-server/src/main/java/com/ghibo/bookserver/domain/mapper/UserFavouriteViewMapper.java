package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.UserBookView;
import com.ghibo.bookserver.domain.dto.UserFavouriteView;
import com.ghibo.bookserver.domain.models.UserBook;
import com.ghibo.bookserver.domain.models.UserFavourite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFavouriteViewMapper {


    UserFavouriteView toUserFavouriteView(UserFavourite userFavourite);

    List<UserFavouriteView> toUserFavouriteView(List<UserFavourite> userFavourites);
}
