package com.ghibo.bookserver.domain.dto.api.volume;

import lombok.Data;

import java.util.List;

@Data
public class ApiSearchVolumes {
    String kind;
    Integer totalItems;
    List<ApiVolume> items;
}
