package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.UserLibrarySmallView;
import com.ghibo.bookserver.domain.dto.UserLibraryView;
import com.ghibo.bookserver.domain.dto.requests.EditLibraryRequest;
import com.ghibo.bookserver.domain.models.UserLibrary;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserLibraryViewMapper {


    UserLibraryView toUserLibraryView(UserLibrary userLibrary);

    List<UserLibraryView> toUserLibraryView(List<UserLibrary> userLibrary);

    List<UserLibrarySmallView> toUserLibrarySmallView(List<UserLibrary> userLibrary);

    @Mapping(target = "count", expression = "java(userLibrary.getBooks() != null? userLibrary.getBooks().size() : 0)")
    UserLibrarySmallView toUserLibrarySmallView(UserLibrary userLibrary);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditLibraryRequest request, @MappingTarget UserLibrary userLibrary);


}
