package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.api.volume.ApiVolumeInfo;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiVolumeMapper {


    Book toBook(ApiVolumeInfo volumeInfo);

    List<Book> toBook(List<ApiVolumeInfo> volumeInfoList);

    default Author toAuthor(String authorName) {
        return new Author(authorName);
    }

    default Category toCategory(String categoryName) {
        return new Category(categoryName);
    }

    default Date toPublishedDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    @AfterMapping
    default void setMissingFields(ApiVolumeInfo apiVolumeInfo, @MappingTarget Book book) {
        if (apiVolumeInfo.getImageLinks() != null) {
            book.setImageUrl(apiVolumeInfo.getImageLinks().getThumbnail());
        }
        if (apiVolumeInfo.getIndustryIdentifiers() != null) {
            apiVolumeInfo.getIndustryIdentifiers().stream().filter(i -> i.getType().equals("ISBN_10")).findFirst().ifPresent(el -> book.setIsbn_10(el.getIdentifier()));
            apiVolumeInfo.getIndustryIdentifiers().stream().filter(i -> i.getType().equals("ISBN_13")).findFirst().ifPresent(el -> book.setIsbn_13(el.getIdentifier()));
        }

    }


}
