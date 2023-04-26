package com.ghibo.bookserver.controllers;


import com.ghibo.bookserver.domain.dto.AuthorView;
import com.ghibo.bookserver.domain.dto.BookView;
import com.ghibo.bookserver.domain.dto.CategoryView;
import com.ghibo.bookserver.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category")
@RestController
@RequestMapping(path = "category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("search")
    public List<CategoryView> search(@RequestParam(required = false) String q) {
        return categoryService.search(q);
    }

    @GetMapping("most-popular")
    public List<CategoryView> mostPopular() {
        return categoryService.getMostPopular();
    }

    @GetMapping("{id}/books")
    public List<BookView> categoryBooks(@PathVariable long id) {
        return categoryService.getCategoryBooks(id);
    }

    @GetMapping("{id}")
    public CategoryView get(@PathVariable long id) {
        return categoryService.getCategoryById(id);
    }
}
