package com.assignment.library.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AddressDTO {
    @NotBlank(message = "Address.houseNo must be present")
    private String houseNo;
    @NotBlank(message = "Address.city must be present")
    private String city;
    @NotBlank(message = "Address.state must be present")
    private String state;

    public AddressDTO(){
        this.houseNo = "";
        this.city = "";
        this.state = "";
    }
}
