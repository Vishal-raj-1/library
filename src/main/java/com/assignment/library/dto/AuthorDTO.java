package com.assignment.library.dto;

import com.assignment.library.dto.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDTO {
    @Id
    private String id;
    @NotBlank(message = "Author.name must be present")
    private String name;
    @NotNull
    private AddressDTO address;

    public AuthorDTO(){
        this.address = new AddressDTO();
    }

    public AuthorDTO(String id, String name){
        this.id = id;
        this.name = name;
        this.address = new AddressDTO();
    }
}
