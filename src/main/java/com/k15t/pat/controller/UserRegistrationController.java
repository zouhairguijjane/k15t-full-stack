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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserRegistrationController {
    public static final String REGISTRATION_PAGE = "registration";
    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/registration.html")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("user", new User());
        return REGISTRATION_PAGE;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/registration.html";
    }

    @GetMapping("/participants.html")
    public String users(Model model) {
        List<User> allRegisteredUsers = userRegistrationService.getAllRegisteredUsers();
        model.addAttribute("allRegisteredUsers", allRegisteredUsers);
        return "participants";
    }

    @PostMapping("/rest/registration")
    public String newRegistration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return REGISTRATION_PAGE;
        } else {
            try {
                userRegistrationService.create(user);
                model.addAttribute("success", true);
                return REGISTRATION_PAGE;
            } catch (UserAlreadyExistException e) {
                model.addAttribute("user_already_exist", e.getMessage());
                return REGISTRATION_PAGE;
            }
        }
    }

    @PostMapping("/api/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody User user, BindingResult bindingResult) {
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
