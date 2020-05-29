package com.k15t.pat.controller;

import com.k15t.pat.exception.UserAlreadyExistException;
import com.k15t.pat.model.User;
import com.k15t.pat.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/registration.html")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            final Set<String> errors = new HashSet<>();
            for (final FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            try {
                User newUser = userRegistrationService.create(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } catch (UserAlreadyExistException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
        }
    }

}
