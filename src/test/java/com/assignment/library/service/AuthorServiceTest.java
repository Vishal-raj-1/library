package com.assignment.library.service;

import com.assignment.library.model.Author;
import com.assignment.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author("1", "John Doe"));
        mockAuthors.add(new Author("2", "Jane Smith"));

        when(authorRepository.findAll()).thenReturn(mockAuthors);

        List<Author> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorById() {
        String existingId = "1";
        Author mockAuthor = new Author(existingId, "John Doe");
        when(authorRepository.findById(existingId)).thenReturn(Optional.of(mockAuthor));
        Author result = authorService.getAuthorById(existingId);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(authorRepository, times(1)).findById(existingId);
    }

    @Test
    void testGetAuthorById_NonExistingId_ReturnsNull() {
        String nonExistingId = "999";
        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Author result = authorService.getAuthorById(nonExistingId);
        assertNull(result);
        verify(authorRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void testSaveAuthor() {
        Author inputAuthor = new Author("1", "John Doe");
        when(authorRepository.save(inputAuthor)).thenReturn(inputAuthor);

        Author result = authorService.saveAuthor(inputAuthor);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(authorRepository, times(1)).save(inputAuthor);
    }

    @Test
    void testGetAuthorsByNameRegex() {
        String nameRegex = "John.*";

        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author("1", "John Doe"));
        mockAuthors.add(new Author("2", "Johnny Bravo"));

        when(authorRepository.findByNameRegex(nameRegex)).thenReturn(mockAuthors);

        List<Author> result = authorService.getAuthorsByNameRegex(nameRegex);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Johnny Bravo", result.get(1).getName());
        verify(authorRepository, times(1)).findByNameRegex(nameRegex);
    }
}
