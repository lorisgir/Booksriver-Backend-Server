package com.ghibo.bookserver.services;

import com.ghibo.bookserver.configuration.feign.GoogleFeignClient;
import com.ghibo.bookserver.domain.dto.api.volume.ApiSearchVolumes;
import com.ghibo.bookserver.domain.dto.api.volume.ApiVolume;
import com.ghibo.bookserver.domain.dto.api.volume.ApiVolumeInfo;
import com.ghibo.bookserver.domain.mapper.ApiVolumeMapper;
import com.ghibo.bookserver.domain.models.Author;
import com.ghibo.bookserver.domain.models.Book;
import com.ghibo.bookserver.domain.models.Category;
import com.ghibo.bookserver.repositories.AuthorRepository;
import com.ghibo.bookserver.repositories.BookRepository;
import com.ghibo.bookserver.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleApiService {

    @Autowired
    GoogleFeignClient googleFeignClient;

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final CategoryRepository categoryRepo;

    private final String apiKey = "<your_api_key_here>";
    private final String apiURL = "https://www.googleapis.com/books/v1/volumes";

    ApiVolumeMapper apiVolumeMapper = Mappers.getMapper(ApiVolumeMapper.class);

    public ApiSearchVolumes searchAndInsert(String q) {
        ApiSearchVolumes apiSearchVolumes = googleFeignClient.getData(q, "en");


        for (ApiVolume item : apiSearchVolumes.getItems()) {
            if (item.getVolumeInfo().getLanguage().equals("en")) {
                insertBook(item.getVolumeInfo());
            }
        }


        return apiSearchVolumes;
    }


    public void insertBook(ApiVolumeInfo volume) {
        Optional<Book> optBook = bookRepo.findByTitleIgnoreCase(volume.getTitle());
        if (optBook.isEmpty()) {
            try {
                Book book = apiVolumeMapper.toBook(volume);

                if (book.getAuthors() != null) {
                    for (Author author : book.getAuthors()) {
                        Optional<Author> optAuthor = authorRepo.findByNameIgnoreCase(author.getName());
                        if (optAuthor.isEmpty()) {
                            authorRepo.save(author);
                        } else {
                            author.setId(optAuthor.get().getId());
                        }
                    }
                }

                if (book.getCategories() != null) {
                    for (Category category : book.getCategories()) {
                        Optional<Category> optCategory = categoryRepo.findByNameIgnoreCase(category.getName());
                        if (optCategory.isEmpty()) {
                            categoryRepo.save(category);
                        } else {
                            category.setId(optCategory.get().getId());
                        }
                    }
                }
                book.setAverageRating(0.0);
                book.setRatingsCount(0);

                bookRepo.save(book);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
