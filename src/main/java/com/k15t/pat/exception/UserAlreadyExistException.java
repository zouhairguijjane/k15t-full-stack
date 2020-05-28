package com.k15t.pat.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String email) {
        super("User already exist with the email address: " + email);
    }
}
