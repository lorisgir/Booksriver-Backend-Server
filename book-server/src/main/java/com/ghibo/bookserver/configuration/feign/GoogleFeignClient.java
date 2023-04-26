package com.ghibo.bookserver.configuration.feign;

import com.ghibo.bookserver.domain.dto.api.volume.ApiSearchVolumes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-feign-client", url = "https://www.googleapis.com/books/v1/")
public interface GoogleFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/volumes")
    ApiSearchVolumes getData(@RequestParam String q, @RequestParam String langRestrict);
}
