package com.ghibo.bookserver.domain.mapper;

import com.ghibo.bookserver.domain.dto.CategoryView;
import com.ghibo.bookserver.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryViewMapper {

    @Mapping(target = "count", expression = "java(category.getBooks() != null? category.getBooks().size() : 0)")
    CategoryView toCategoryView(Category category);

    List<CategoryView> toCategoryView(List<Category> categories);


}
