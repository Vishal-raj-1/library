package com.assignment.library.service;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.model.Author;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.utils.AuthorDTOEntityConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(
                new Author("1", "John Doe"),
                new Author("2", "Jane Smith")
        );

        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDTO> result = authorService.getAllAuthors();

        List<AuthorDTO> expectedAuthors = authors
                .stream()
                .map(AuthorDTOEntityConverter::entityToDTO)
                .toList();

        verify(authorRepository, times(1)).findAll();
        assertEquals(expectedAuthors, result);
    }

    @Test
    void testGetAuthorById() {
        String existingId = "1";
        Author author = new Author(existingId, "John Doe");

        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        AuthorDTO result = authorService.getAuthorById(existingId);

        verify(authorRepository, times(1)).findById(existingId);
        assertEquals(AuthorDTOEntityConverter.entityToDTO(author), result);
    }

    @Test
    void testGetAuthorById_NonExistingId_ReturnsNull() {
        String nonExistingId = "999";

        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            authorService.getAuthorById(nonExistingId);
        });

        verify(authorRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void testSaveAuthor() {
        Author inputAuthor = new Author("1", "John Doe");

        when(authorRepository.save(inputAuthor)).thenReturn(inputAuthor);

        AuthorDTO expectedAuthor = AuthorDTOEntityConverter.entityToDTO(inputAuthor);
        AuthorDTO result = authorService.saveAuthor(expectedAuthor);

        verify(authorRepository, times(1)).save(inputAuthor);
        assertNotNull(result);
        assertEquals(expectedAuthor, result);
    }

    @Test
    void testGetAuthorsByNameRegex() {
        String nameRegex = "John.*";

        List<Author> authors = Arrays.asList(
                new Author("1", "John Doe"),
                new Author("2", "Johnny Bravo")
        );

        when(authorRepository.findByNameRegex(nameRegex)).thenReturn(authors);

        List<AuthorDTO> expectedAuthors = authors
                .stream()
                .map(AuthorDTOEntityConverter::entityToDTO)
                .toList();

        List<AuthorDTO> result = authorService.getAuthorsByNameRegex(nameRegex);

        verify(authorRepository, times(1)).findByNameRegex(nameRegex);
        assertNotNull(result);
        assertEquals(expectedAuthors, result);
    }
}
