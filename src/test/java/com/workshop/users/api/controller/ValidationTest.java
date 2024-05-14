package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataInitzializerController;
import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.UserDto;
import static org.assertj.core.api.Assertions.*;

import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.workshop.users.api.controller.Validations;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class ValidationTest {

    private UserService userService;
    private AddressService addressService;
    private InitializerController initializerController;
    private AddressDto addressDto;
    private CountryDto countryDto;
    private UserDto userDto;
    private Validations validations;
    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        addressService = mock(AddressService.class);
        initializerController = new InitializerController(userService,addressService);
        validations = new Validations();
        countryDto = DataInitzializerController.COUNTRY_SPAIN;
        addressDto = DataInitzializerController.ADDRESS_VALLECAS;
        userDto = DataInitzializerController.USER_LOGGED;
    }

    @Test
    void checkMailTest() throws ParseException {
        Assertions.assertThat(validations.checkEmail(userDto))
                .isTrue();
    }

    @Test
    void checkPasswordTest() throws ParseException {
        Assertions.assertThat(validations.checkPassword(userDto))
                .isTrue();
    }

    @Test
    void checkDateFormatTest() throws ParseException {
        Assertions.assertThat(validations.checkDateFormat(userDto))
                .isTrue();
    }

    @Test
    void checkAgeTest() throws ParseException {
        Assertions.assertThat(validations.checkAge(userDto))
                .isTrue();
    }

    @Test
    void checkAllMethodsUserTest() throws ParseException {
        Assertions.assertThat(validations.checkAllMethodsUser(userDto))
                .isTrue();
    }

    @Test
    void name() {
    }
}
