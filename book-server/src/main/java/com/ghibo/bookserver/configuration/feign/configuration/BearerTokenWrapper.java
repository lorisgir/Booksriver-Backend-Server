package com.ghibo.bookserver.configuration.feign.configuration;

import lombok.Data;

@Data
public class BearerTokenWrapper {
    private String token;
}
