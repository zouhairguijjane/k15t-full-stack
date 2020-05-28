package com.k15t.pat.service;

import com.k15t.pat.exception.UserAlreadyExistException;
import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) throws UserAlreadyExistException {
        String userEmail = user.getEmail();

        if (userRepository.findByEmailIgnoreCase(userEmail).isPresent()) {
            throw new UserAlreadyExistException(userEmail);
        }

        return userRepository.save(user);
    }
}
