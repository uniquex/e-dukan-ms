package com.example.edukan.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Surname can not be blank")
    private String surname;
    @Email(message = "Wrong email format")
    private String email;
    @NotBlank(message = "Phone can not be blank")
    private String phone;
    private String address;

    @NotBlank
    @Size(min = 8, message = "Password's length should be more than 8 digits")
    private String password;
}