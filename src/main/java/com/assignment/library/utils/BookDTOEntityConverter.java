package com.assignment.library.utils;

import com.assignment.library.dto.BookDTO;
import com.assignment.library.model.Book;
import org.springframework.beans.BeanUtils;

public class BookDTOEntityConverter {

    public static BookDTO entityToDTO(Book book){
        BookDTO bookdto = new BookDTO();
        BeanUtils.copyProperties(book, bookdto);
        return bookdto;
    }

    public static Book dtoToEntity(BookDTO bookDto){
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        return book;
    }
}
