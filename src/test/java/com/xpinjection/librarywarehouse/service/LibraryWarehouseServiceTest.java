package com.xpinjection.librarywarehouse.service;

import com.xpinjection.librarywarehouse.adaptors.httpclient.LibraryClient;
import com.xpinjection.librarywarehouse.adaptors.httpclient.dto.BookDto;
import com.xpinjection.librarywarehouse.service.impl.LibraryWarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryWarehouseServiceTest {
    private LibraryWarehouseService service;

    @Mock
    private LibraryClient libraryClient;

    @BeforeEach
    void init() {
        service = new LibraryWarehouseServiceImpl(libraryClient);
    }

    @Test
    void ifNoBooksFoundForAuthorThenOrderSomeAndAddToTheLibrary() {
        when(libraryClient.findByAuthor("a")).thenReturn(emptyList());

        service.restockBooksForAuthor("a");

        verify(libraryClient).addBooks(notNull());
    }

    @Test
    void ifBooksFoundForAuthorThenDoNotOrderMore() {
        when(libraryClient.findByAuthor("a")).thenReturn(singletonList(new BookDto()));

        service.restockBooksForAuthor("a");

        verify(libraryClient, never()).addBooks(notNull());
    }
}