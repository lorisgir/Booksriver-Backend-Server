package com.ghibo.bookserver.configuration.feign;

import com.ghibo.bookserver.domain.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "USER-SERVER")
public interface AuthFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/user/meSmall")
    User getMe();
}
