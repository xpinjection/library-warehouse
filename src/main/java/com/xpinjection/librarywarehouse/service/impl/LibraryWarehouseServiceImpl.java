package com.xpinjection.librarywarehouse.service.impl;

import com.xpinjection.librarywarehouse.adaptors.httpclient.LibraryClient;
import com.xpinjection.librarywarehouse.service.LibraryWarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryWarehouseServiceImpl implements LibraryWarehouseService {
    private final LibraryClient libraryClient;

    @Override
    public void restockBooksForAuthor(String author) {
        LOG.info("Restock books for the author: {}", author);
        var books = libraryClient.findByAuthor(author);
        if (books.isEmpty()) {
            LOG.info("There are no books in the library for the author: {}", author);
            var newBooks = buyBooks(author).stream()
                    .collect(toMap(identity(), _ -> author));
            LOG.info("Add new books to the library: {}", newBooks);
            libraryClient.addBooks(newBooks);
        }
    }

    private List<String> buyBooks(String author) {
        LOG.info("Buy new books for the author: {}", author);
        return asList("New book #1", "New book #2");
    }
}
