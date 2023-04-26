package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.AuthorView;
import com.ghibo.bookserver.domain.models.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorViewMapper {


    AuthorView toAuthorView(Author author);

    List<AuthorView> toAuthorView(List<Author> authors);


}
