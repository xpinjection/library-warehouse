package com.xpinjection.librarywarehouse.adaptors.httpclient;

import com.xpinjection.librarywarehouse.adaptors.httpclient.dto.BookDto;
import com.xpinjection.test.FeignClientTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@FeignClientTest(stubs = "classpath*:wiremock/**/*.json")
class LibraryClientIntegrationTest {
    @Autowired
    private LibraryClient libraryClient;

    @Test
    void ifBooksFoundByAuthorThenTheyAreReturned() {
        var books = libraryClient.findByAuthor("Josh Bloch");

        assertThat(books).contains(new BookDto(9L, "Effective Java", "Josh Bloch"));
    }

    @Test
    void ifNoBooksFoundByAuthorThenReturnEmptyList() {
        var books = libraryClient.findByAuthor("Unknown");

        assertThat(books).isEmpty();
    }
}