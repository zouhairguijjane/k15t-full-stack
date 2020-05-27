package com.k15t.pat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 200, message
            = "Name must be between 2 and 64 characters")
    @NotEmpty(message = "Please fill in your name")
    private String name;

    @NotEmpty(message = "Please fill in your email")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Please fill in your address")
    private String address;

    @NotEmpty(message = "Please fill in your password")
    @Size(min = 8, max = 64, message
            = "Password must be between 8 and 64 characters")
    private String password;

    @Pattern(regexp = "^[+]?[0-9]{1,4}[-\\s./0-9]*$", message = "Phone number should be like: +33612345678 or +33-6-12-34-56-78")
    private String phoneNumber;
}
