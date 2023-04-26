package com.ghibo.userserver.configuration.feign;

import com.ghibo.userserver.domain.dto.BookView;
import com.ghibo.userserver.domain.dto.UserLibraryView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "BOOK-SERVER")
public interface BookFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/book/{id}")
    BookView getBook(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value = "/user-library/user/{id}/libraries")
    List<UserLibraryView> getUserLibraries(@PathVariable("id") long id);
}
