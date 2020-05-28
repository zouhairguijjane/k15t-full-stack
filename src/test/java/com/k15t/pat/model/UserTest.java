package com.k15t.pat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    public static final String EMAIL = "zh.guijjane@gmail.com";
    public static final String NAME = "Zouhair";
    public static final String PARIS = "Paris";
    public static final String PASSWORD = "123456789";
    public static final String FRENCH_PHONE_NUMBER = "+33629166535";
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void createNewUser() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    void createUserWithShortName() {
        User user = User.builder()
                .name("z")
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name must be between 2 and 64 characters");
    }

    @Test
    void createUserWithDigitsInName() {
        User user = User.builder()
                .name("Zouhair 1")
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator()
                .next()
                .getMessage()).isEqualTo("Name must contains only letters (White space is allowed)");
    }

    @Test
    void createUserWithoutName() {
        User user = User.builder()
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Please fill in your name");
    }

    @Test
    void createUserWithWrongEmail() {
        User user = User.builder()
                .name(NAME)
                .email("aa.aa")
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email should be valid");
    }

    @Test
    void createUserWithoutEmail() {
        User user = User.builder()
                .name(NAME)
                .address(PARIS)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Please fill in your email");
    }

    @Test
    void createUserWithoutAddress() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Please fill in your address");
    }

    @Test
    void createUserWithShortPassword() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .address(PARIS)
                .password("123456")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password must be between 8 and 64 characters");
    }

    @Test
    void createUserWithEmptyPassword() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .address(PARIS)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage()).isEqualTo("Please fill in your password");
    }

    @Test
    void createUserWithPhoneNumber() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .phoneNumber(FRENCH_PHONE_NUMBER)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    void createUserWithWrongPhoneNumber() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .address(PARIS)
                .password(PASSWORD)
                .phoneNumber("0651891aa")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator()
                .next()
                .getMessage()).isEqualTo("Phone number should be like: +33612345678 or +33-6-12-34-56-78");
    }
}
