package com.k15t.pat.service;

import com.k15t.pat.exception.UserAlreadyExistException;
import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) throws UserAlreadyExistException {
        log.info("Attempting to create a new user with the email address: {}", user.getEmail());
        String userEmail = user.getEmail();

        if (isUserAlreadyExisting(userEmail)) {
            log.info("User already exist with the email address: {}", user.getEmail());
            throw new UserAlreadyExistException(userEmail);
        }

        log.info("A new user is saved with the email address: {}", user.getEmail());
        return userRepository.save(user);
    }

    private boolean isUserAlreadyExisting(String userEmail) {
        return userRepository.findByEmailIgnoreCase(userEmail).isPresent();
    }
}
