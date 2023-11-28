package com.assignment.library.controller;

import com.assignment.library.DTO.BookAuthorDTO;
import com.assignment.library.model.Book;
import com.assignment.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable String bookId){
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/byGenre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre) {
        List<Book> books = bookService.getBooksByGenre(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/byGenreAndCopiesAvailable")
    public ResponseEntity<List<Book>> getBooksByGenreAndCopiesAvailable(
            @RequestParam String genre,
            @RequestParam int copiesAvailable) {
        List<Book> books = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookAuthorDTO request) {
        String authorName = request.getAuthorName();
        Book book = request.getBook();

        Book savedBook = bookService.saveBook(book, authorName);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/byAuthors")
    public ResponseEntity<List<Book>> getBooksByAuthorIds(@RequestBody List<String> authors) {
        List<Book> books = bookService.getBooksByAuthorIds(authors);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
