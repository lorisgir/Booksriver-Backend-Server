package com.ghibo.bookserver.domain.dto.api.volume;

import lombok.Data;

@Data
public class ApiVolume {
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private ApiVolumeInfo volumeInfo;
}
