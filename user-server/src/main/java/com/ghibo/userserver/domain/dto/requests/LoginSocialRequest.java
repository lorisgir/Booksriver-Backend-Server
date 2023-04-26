package com.ghibo.userserver.domain.dto.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginSocialRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String id;
}
