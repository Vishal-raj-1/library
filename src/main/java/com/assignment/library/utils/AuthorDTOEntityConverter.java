package com.assignment.library.utils;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.model.Author;
import org.springframework.beans.BeanUtils;

public class AuthorDTOEntityConverter {
    public static AuthorDTO entityToDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        BeanUtils.copyProperties(author, authorDTO);
        return authorDTO;
    }

    public static Author dtoToEntity(AuthorDTO authorDTO){
        Author author = new Author();
        BeanUtils.copyProperties(authorDTO, author);
        return author;
    }
}
