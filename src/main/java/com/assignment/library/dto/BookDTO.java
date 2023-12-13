package com.assignment.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class BookDTO {
    private String id;
    @NotBlank(message = "Book.genre must be present")
    private String genre;
    @NotBlank(message = "Book.authorID must be present")
    private String authorId;
    @NotNull
    @Positive(message = "Book.copiesAvailable must be positive")
    private int copiesAvailable;

    public BookDTO(String id, String genre, String authorId, int copiesAvailable){
        this.id = id;
        this.genre = genre;
        this.authorId = authorId;
        this.copiesAvailable = copiesAvailable;
    }
}
