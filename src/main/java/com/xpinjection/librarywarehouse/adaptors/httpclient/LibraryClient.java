package com.xpinjection.librarywarehouse.adaptors.httpclient;

import com.xpinjection.librarywarehouse.adaptors.httpclient.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "library", url = "${feign.library.url}")
public interface LibraryClient {
    @GetMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<BookDto> findByAuthor(@RequestParam String author);

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BookDto> addBooks(@RequestBody Map<String, String> books);
}
