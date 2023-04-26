package com.ghibo.userserver.domain.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateReviewRequest {

    @NotBlank
    private long bookId;
    @NotBlank
    private String text;
    @NotBlank
    private Double score;
}
