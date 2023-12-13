package com.assignment.library.controller;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.model.Author;
import com.assignment.library.model.Book;
import com.assignment.library.service.AuthorService;
import com.assignment.library.service.BookService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable String authorId) {
        AuthorDTO author = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/byNameRegex")
    public ResponseEntity<List<AuthorDTO>> getAllAuthorsByNameRegex(@RequestParam String nameRegex) {
        List<AuthorDTO> authors = authorService.getAuthorsByNameRegex(nameRegex);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }
}
