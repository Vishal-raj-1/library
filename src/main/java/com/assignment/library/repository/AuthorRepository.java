package com.assignment.library.repository;

import com.assignment.library.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByName(String authorName);
    @Query("{ name: { $regex : ?0 } }")
    List<Author> findByNameRegex(String nameRegex);
}