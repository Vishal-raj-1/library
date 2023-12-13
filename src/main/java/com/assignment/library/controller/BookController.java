package com.assignment.library.controller;

import com.assignment.library.dto.BookDTO;
import com.assignment.library.dto.SaveBookDTO;
import com.assignment.library.model.Book;
import com.assignment.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String bookId){
        BookDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/byGenre")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@RequestParam String genre) {
        List<BookDTO> books = bookService.getBooksByGenre(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/byGenreAndCopiesAvailable")
    public ResponseEntity<List<BookDTO>> getBooksByGenreAndCopiesAvailable(
            @RequestParam String genre,
            @RequestParam int copiesAvailable) {
        List<BookDTO> books = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody @Valid SaveBookDTO request) {
        String authorName = request.getAuthorName();
        BookDTO book = request.getBook();

        BookDTO savedBook = bookService.saveBook(book, authorName);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/byAuthors")
    public ResponseEntity<List<BookDTO>> getBooksByAuthorName(@RequestBody List<String> authorNames) {
        List<BookDTO> books = bookService.getBooksByAuthorNames(authorNames);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
