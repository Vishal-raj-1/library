package com.assignment.library.service;

import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private List<Book> expectedBooks = Arrays.asList(new Book("123", "Science Fiction", "345", 8),
            new Book("236", "Science Fiction", "789", 10));
    @Test
    void testGetBookById() {
        // Arrange
        String bookId = "123";
        Book expectedBook = new Book(bookId, "tech", "356", 8);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        // Act
        Book result = bookService.getBookById(bookId);

        // Assert
        assertEquals(expectedBook, result);
    }

    @Test
    void testGetAllBooks() {
        // Arrange

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(expectedBooks, result);
    }
    @Test
    void testGetBooksByGenre() {
        // Arrange
        String genre = "Science Fiction";
        when(bookRepository.findByGenre(genre)).thenReturn(expectedBooks);

        // Act
        List<Book> result = bookService.getBooksByGenre(genre);

        // Assert
        assertEquals(2, result.size());
        // Additional assertions if needed
    }
    @Test
    void testGetBooksByGenreAndCopiesAvailable() {
        // Arrange
        String genre = "Science Fiction";
        int copiesAvailable = 5;
        when(bookRepository.findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable))
                .thenReturn(expectedBooks);

        // Act
        List<Book> result = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);

        // Assert
        assertEquals(2, result.size());
    }

    // Add more tests for other service methods
}
