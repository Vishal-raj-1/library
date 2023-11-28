package com.assignment.library.controller;

import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.service.AuthorService;
import com.assignment.library.service.BookService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String authorId) {
        Author authors = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/byNameRegex")
    public ResponseEntity<List<Author>> getAllAuthorsByNameRegex(@RequestParam String nameRegex) {
        List<Author> authors = authorService.getAuthorsByNameRegex(nameRegex);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }
}
