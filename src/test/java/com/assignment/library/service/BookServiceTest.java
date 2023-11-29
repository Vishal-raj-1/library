package com.assignment.library.service;

import com.assignment.library.model.Author;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private final List<Book> expectedBooks = Arrays.asList(new Book("123", "Science Fiction", "345", 8),
            new Book("236", "Science Fiction", "789", 10));
    @Test
    void testGetBookById() {
        String bookId = "123";
        Book expectedBook = new Book(bookId, "tech", "356", 8);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));
        Book result = bookService.getBookById(bookId);
        assertEquals(expectedBook, result);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(expectedBooks);
        List<Book> result = bookService.getAllBooks();
        assertEquals(expectedBooks, result);
    }
    @Test
    void testGetBooksByGenre() {
        String genre = "Science Fiction";
        when(bookRepository.findByGenre(genre)).thenReturn(expectedBooks);
        List<Book> result = bookService.getBooksByGenre(genre);
        assertEquals(2, result.size());
    }
    @Test
    void testGetBooksByGenreAndCopiesAvailable() {
        String genre = "Science Fiction";
        int copiesAvailable = 5;
        when(bookRepository.findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable))
                .thenReturn(expectedBooks);
        List<Book> result = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);
        assertEquals(2, result.size());
    }


    @Test
    void testGetBooksByAuthorIds() {
        List<String> authorNames = Arrays.asList("Author1", "Author2");

        Author author1 = new Author();
        author1.setName("Author1");
        author1.setBookList(Arrays.asList("123", "234"));

        Author author2 = new Author();
        author2.setName("Author2");
        author2.setBookList(Arrays.asList("345", "456"));

        when(authorRepository.findByName("Author1")).thenReturn(author1);
        when(authorRepository.findByName("Author2")).thenReturn(author2);

        when(bookRepository.findById("123")).thenReturn(Optional.of(new Book("123", "Science Fiction", "234", 8)));
        when(bookRepository.findById("234")).thenReturn(Optional.of(new Book("234", "Science Fiction", "234", 8)));
        when(bookRepository.findById("345")).thenReturn(Optional.of(new Book("345", "STech", "443", 8)));
        when(bookRepository.findById("456")).thenReturn(Optional.of(new Book("456", "Science Fiction", "443", 8)));

        List<Book> result = bookService.getBooksByAuthorNames(authorNames);

        assertEquals(4, result.size());
        verify(authorRepository, times(2)).findByName(anyString());
        verify(bookRepository, times(4)).findById(anyString());
    }

    @Test
    void testSaveBook() {
        Book inputBook = new Book();
        inputBook.setGenre("Fiction");
        inputBook.setCopiesAvailable(5);

        String authorName = "John Doe";

        Author existingAuthor = new Author();
        existingAuthor.setId("authorId");
        existingAuthor.setName(authorName);

        when(authorRepository.findByName(authorName)).thenReturn(existingAuthor);
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId("123");
            return savedBook;
        });

        Book result = bookService.saveBook(inputBook, authorName);

        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals(existingAuthor.getId(), result.getAuthorId());
        verify(authorRepository, times(1)).findByName(authorName);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(authorRepository, times(1)).save(existingAuthor);
    }
}

