package com.assignment.library.service;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.dto.BookDTO;
import com.assignment.library.dto.SaveBookDTO;
import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import com.assignment.library.utils.BookDTOEntityConverter;
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

    private final List<Book> books = Arrays.asList(
            new Book("123", "Science Fiction", "345", 8),
            new Book("236", "Science Fiction", "789", 10)
    );

    private final List<BookDTO> expectedBooks = books
            .stream()
            .map(BookDTOEntityConverter::entityToDTO)
            .toList();

    @Test
    void testGetBookById() {
        String bookId = "123";
        Book book = new Book(bookId, "tech", "356", 8);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        BookDTO result = bookService.getBookById(bookId);

        verify(bookRepository, times(1)).findById(bookId);
        BookDTO expectedBook = BookDTOEntityConverter.entityToDTO(book);
        assertEquals(expectedBook, result);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDTO> result = bookService.getAllBooks();

        verify(bookRepository, times(1)).findAll();
        assertEquals(expectedBooks, result);
    }
    @Test
    void testGetBooksByGenre() {
        String genre = "Science Fiction";

        when(bookRepository.findByGenre(genre)).thenReturn(books);
        List<BookDTO> result = bookService.getBooksByGenre(genre);

        verify(bookRepository, times(1)).findByGenre(genre);
        assertEquals(expectedBooks, result);
    }
    @Test
    void testGetBooksByGenreAndCopiesAvailable() {
        String genre = "Science Fiction";
        int copiesAvailable = 5;

        when(bookRepository.findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable))
                .thenReturn(books);

        List<BookDTO> result = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);

        verify(bookRepository, times(1))
                .findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable);
        assertEquals(expectedBooks, result);
    }




    @Test
    void testSaveBook() {
        BookDTO inputBook = new BookDTO();
        inputBook.setGenre("Fiction");
        inputBook.setCopiesAvailable(5);

        String authorName = "John Doe";
        Author existingAuthor = new Author();
        existingAuthor.setId("1");
        existingAuthor.setName(authorName);

        when(authorRepository.findByName(authorName)).thenReturn(existingAuthor);

        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId("123");
            return savedBook;
        });

        BookDTO result = bookService.saveBook(inputBook, authorName);

        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals(existingAuthor.getId(), result.getAuthorId());

        verify(authorRepository, times(1)).findByName(authorName);
        verify(bookRepository, times(1)).save(argThat(book -> true));
    }

}

