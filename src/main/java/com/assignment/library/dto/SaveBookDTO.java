package com.assignment.library.dto;


import lombok.Data;

@Data
public class SaveBookDTO {
    private BookDTO book;
    private String authorName;
}
