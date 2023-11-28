package com.assignment.library.repository;

import com.assignment.library.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    @Query("{ name : ?0 }")
    Author findByName(String authorName);
    @Query("{ name: { $regex : ?0 } }")
    List<Author> findByNameRegex(String nameRegex);
}