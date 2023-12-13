package com.assignment.library.repository;

import com.assignment.library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByGenre(String genre);
    @Query("{ genre : ?0 , copiesAvailable : { $gt : ?1 } }")
    List<Book> findByGenreAndCopiesAvailableGreaterThan(String genre, int copiesAvailable);
    List<Book> findByAuthorIdIn(List<String> authorId);
}
