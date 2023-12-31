package com.assignment.library.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "books")
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    @NotBlank(message = "Book.genre must be present")
    private String genre;
    private String authorId;
    @NotNull
    @Positive
    private int copiesAvailable;

    public Book(String id, String genre, String authorId, int copiesAvailable){
        this.id = id;
        this.genre = genre;
        this.authorId = authorId;
        this.copiesAvailable = copiesAvailable;
    }
}
