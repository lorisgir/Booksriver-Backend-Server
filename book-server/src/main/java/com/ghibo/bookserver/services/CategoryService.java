package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.domain.dto.AuthorView;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.CategoryView;
import com.ghibo.bookserver.domain.mapper.BookViewMapper;
import com.ghibo.bookserver.domain.mapper.CategoryViewMapper;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.Category;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final BookRepository bookRepository;

    CategoryViewMapper categoryViewMapper = Mappers.getMapper(CategoryViewMapper.class);
    BookViewMapper bookViewMapper = Mappers.getMapper(BookViewMapper.class);

    @Autowired
    SessionParam sessionParam;

    public List<CategoryView> search(String q) {
        List<Category> categories = categoryRepo.findTop100ByNameContainingIgnoreCase(q);
        return categoryViewMapper.toCategoryView(categories);
    }

    public List<CategoryView> getMostPopular() {
        List<Category> categories = categoryRepo.fetchRandom();
        return categoryViewMapper.toCategoryView(categories);
    }

    public List<BookView> getCategoryBooks(long id) {
        sessionParam.setUserFilter();

        Category category = categoryRepo.getById(id);
        List<Book> books = bookRepository.findTop100ByCategoriesId(category.getId());
        return bookViewMapper.toBookView(books);
    }

    public CategoryView getCategoryById(long id) {
        Category category = categoryRepo.getById(id);
        return categoryViewMapper.toCategoryView(category);
    }


}
