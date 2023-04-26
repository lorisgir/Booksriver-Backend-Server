package com.ghibo.bookserver.domain.dto.api.volume;

import lombok.Data;

import java.util.List;

@Data
public class ApiVolumeInfo {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private Integer pageCount;
    private List<String> categories;
    private Double averageRating;
    private Integer ratingsCount;
    private String language;
    private List<ApiIndustryIdentifiers> industryIdentifiers;
    private ApiImageLinks imageLinks;


}

