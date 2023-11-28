package com.assignment.library.service;

import com.assignment.library.model.Author;
import com.assignment.library.repository.AuthorRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthorsByNameRegex(String nameRegex) {
        return authorRepository.findByNameRegex(nameRegex);
    }
}
