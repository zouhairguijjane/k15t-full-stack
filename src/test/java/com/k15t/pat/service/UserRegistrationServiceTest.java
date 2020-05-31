package com.k15t.pat.service;

import com.k15t.pat.exception.UserAlreadyExistException;
import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    @Test
    public void shouldSaveUserSuccessfully() throws UserAlreadyExistException {
        User user = User.builder()
                .name("Zouhair")
                .email("example@test.com")
                .address("Paris")
                .password("1234546789")
                .phoneNumber("+33612345678")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userRegistrationService.create(user);

        assertThat(createdUser).isNotNull();
        verify(userRepository).save((any(User.class)));
    }

    @Test
    public void shouldThrowExceptionWhenEmailAlreadyExist() {
        User user = User.builder()
                .id(1L)
                .name("Zouhair")
                .email("example@test.com")
                .address("Paris")
                .password("1234546789")
                .phoneNumber("+33612345678")
                .build();

        given(userRepository.findByEmailIgnoreCase(user.getEmail())).willReturn(Optional.of(user));

        assertThatThrownBy(() -> userRegistrationService.create(user)).isInstanceOf(Exception.class)
                .hasMessageContaining("User already exist with the email address:");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void listAllUsers() {
        User zouhair = User.builder()
                .id(1L)
                .name("Zouhair")
                .email("zouhair@test.com")
                .address("Paris")
                .password("1234546789")
                .phoneNumber("+33612345678")
                .build();

        User salma = User.builder()
                .id(2L)
                .name("Salma")
                .email("salma@test.com")
                .address("Ffm")
                .password("1234546789")
                .phoneNumber("+33612345678")
                .build();

        List<User> users = asList(zouhair, salma);

        when(userRegistrationService.getAllRegisteredUsers()).thenReturn(asList(zouhair, salma));

        List<User> allRegisteredUsers = userRegistrationService.getAllRegisteredUsers();

        assertThat(allRegisteredUsers).containsExactlyInAnyOrderElementsOf(users);
    }
}
