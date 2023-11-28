package com.assignment.library.DTO;

import com.assignment.library.model.Book;

public class BookAuthorDTO {
    private Book book;
    private String authorName;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
