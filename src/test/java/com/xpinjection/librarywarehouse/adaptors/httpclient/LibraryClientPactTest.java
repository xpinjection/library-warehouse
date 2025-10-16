package com.xpinjection.librarywarehouse.adaptors.httpclient;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.xpinjection.librarywarehouse.adaptors.httpclient.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMaxLike;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(PactConsumerTestExt.class)
@ActiveProfiles("pact")
@PactTestFor(providerName = "com.xpinjection.library", pactVersion = PactSpecVersion.V3)
class LibraryClientPactTest {
    private static final String BOOK_NAME = "Effective Java";
    private static final String AUTHOR = "Josh Bloch";

    @Autowired
    private LibraryClient libraryClient;

    @Pact(consumer = "com.xpinjection.library-warehouse")
    public RequestResponsePact foundBooks(PactDslWithProvider builder) {
        return builder
                .given("books exist for author", "author", AUTHOR)
                .uponReceiving("find books for author")
                    .path("/books")
                    .query("author=" + AUTHOR)
                .willRespondWith()
                    .status(200)
                    .body(newJsonArrayMinLike(1, array -> array.object(book -> {
                        book.id("id", 9L);
                        book.stringMatcher("name", "\\w.*", BOOK_NAME);
                        book.stringMatcher("author", AUTHOR);
                    })).build())
                .toPact();
    }

    @Pact(consumer = "com.xpinjection.library-warehouse")
    public RequestResponsePact noBooks(PactDslWithProvider builder) {
        return builder
                .given("no books for author", "author", "Unknown")
                .uponReceiving("find books for author")
                    .path("/books")
                    .query("author=Unknown")
                .willRespondWith()
                    .status(200)
                    .body(newJsonArrayMaxLike(0, array -> {}).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "foundBooks", port = "8080")
    void ifBooksFoundByAuthorThenTheyAreReturned() {
        var books = libraryClient.findByAuthor(AUTHOR);

        assertThat(books).contains(new BookDto(9L, BOOK_NAME, AUTHOR));
    }

    @Test
    @PactTestFor(pactMethod = "noBooks", port = "8080")
    void ifNoBooksFoundByAuthorThenReturnEmptyList() {
        var books = libraryClient.findByAuthor("Unknown");

        assertThat(books).isEmpty();
    }
}