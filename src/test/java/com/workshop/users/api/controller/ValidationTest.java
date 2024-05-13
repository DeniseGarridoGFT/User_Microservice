package com.workshop.users.api.controller;

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
        UserDto userDto = UserDto.builder()
                .id(2L)
                .name("Denise")
                .lastName("Garrido")
                .email("denise@gmail.com")
                .address(null)
                .birthDate("2004/14/04")
                .password("password")
                .phone("123456789")
                .fidelityPoints(100)
                .country(null)
                .build();



//        DataToUserControllerTesting.USER_ID_2.setEmail("invalid email");
//        UserDto userDto = DataToUserControllerTesting.USER_ID_2;


        validations = new Validations();
    }

    @Test
    void checkMailTest() throws ParseException {
        Assertions.assertThat(validations.checkEmail(userDto))
                .isTrue();
    }
}
