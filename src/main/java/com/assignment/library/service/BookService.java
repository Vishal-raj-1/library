package com.assignment.library.service;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public List<Book> getBooksByGenreAndCopiesAvailable(String genre, int copiesAvailable) {
        return bookRepository.findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book, String authorName) {
        if( book!=null && book.getCopiesAvailable()>=0 && !authorName.isEmpty() && book.getGenre()!=null ) {
            Author author = authorRepository.findByName(authorName);
            if(author == null){
                author = new Author();
                author.setName(authorName);

                String authorId = new ObjectId().toHexString();
                author.setId(authorId);

                authorRepository.save(author);
                book.setAuthorId(authorId);
            } else {
                book.setAuthorId(author.getId());
            }

            Book savedBook = bookRepository.save(book);
            author.getBookList().add(savedBook.getId());
            authorRepository.save(author);

            return savedBook;
        } else{
            throw new IllegalArgumentException("Invalid/Incomplete details given");
        }
    }

    public List<Book> getBooksByAuthorIds(List<String> authors) {
        List<Book> books = new ArrayList<>();

        for (String authorName : authors) {
            Author author = authorRepository.findByName(authorName);
            List<String> bookIds = author.getBookList();
            System.out.println("Author: " + authorName);
            for(String bookId: bookIds) {
                Optional<Book> book = bookRepository.findById(bookId);

                book.ifPresent(books::add);
            }
        }

        return books;
    }
}
