package com.ghibo.userserver.domain.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String username;

}
