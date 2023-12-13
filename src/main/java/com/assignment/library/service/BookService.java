package com.assignment.library.service;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.dto.BookDTO;
import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import com.assignment.library.utils.BookDTOEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(BookDTOEntityConverter::entityToDTO)
                .toList();
    }

    public List<BookDTO> getBooksByGenre(String genre) {
        return bookRepository
                .findByGenre(genre)
                .stream()
                .map(BookDTOEntityConverter::entityToDTO)
                .toList();
    }

    public List<BookDTO> getBooksByGenreAndCopiesAvailable(String genre, int copiesAvailable) {
        return bookRepository
                .findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable)
                .stream()
                .map(BookDTOEntityConverter::entityToDTO)
                .toList();
    }

    public BookDTO getBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            return BookDTOEntityConverter.entityToDTO(book.get());
        } else {
            throw new NoSuchElementException("No Book found with id: " + id);
        }
    }

    public BookDTO saveBook(BookDTO book, String authorName) {
        Author author = authorRepository.findByName(authorName);
        if(author == null){
            throw new NoSuchElementException("Author Named " + authorName + " Not Found");
        }

        book.setAuthorId(author.getId());
        Book savedBook = bookRepository.save(BookDTOEntityConverter.dtoToEntity(book));

        return BookDTOEntityConverter.entityToDTO(savedBook);
    }

    public List<BookDTO> getBooksByAuthorNames(List<String> authorNames) {
        List<String> authorIds = new ArrayList<>();

        authorNames.forEach(authorName -> {
            Author author = authorRepository.findByName(authorName);

            if(author != null){
                authorIds.add(author.getId());
            }
        });

        return bookRepository
                .findByAuthorIdIn(authorIds)
                .stream()
                .map(BookDTOEntityConverter::entityToDTO)
                .toList();
    }
}
