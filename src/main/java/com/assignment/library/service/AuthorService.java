package com.assignment.library.service;

import com.assignment.library.dto.AuthorDTO;
import com.assignment.library.model.Author;
import com.assignment.library.repository.AuthorRepository;
import com.assignment.library.utils.AuthorDTOEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(AuthorDTOEntityConverter::entityToDTO)
                .toList();
    }

    public AuthorDTO getAuthorById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            return AuthorDTOEntityConverter.entityToDTO(author.get());
        }
        else {
            throw new NoSuchElementException("Author not found with Id: " + id);
        }
    }

    public AuthorDTO saveAuthor(AuthorDTO authorDto) {
        Author author = AuthorDTOEntityConverter.dtoToEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return AuthorDTOEntityConverter.entityToDTO(savedAuthor);
    }

    public List<AuthorDTO> getAuthorsByNameRegex(String nameRegex) {
        return authorRepository
                .findByNameRegex(nameRegex)
                .stream()
                .map(AuthorDTOEntityConverter::entityToDTO)
                .toList();
    }
}
