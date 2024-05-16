package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataInitzializerController;
import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;

import java.text.ParseException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        initializerController = new InitializerController(userService, addressService);
        validations = new Validations(userService);
        countryDto = DataInitzializerController.COUNTRY_SPAIN;
        addressDto = DataInitzializerController.ADDRESS_VALLECAS;
        userDto = DataInitzializerController.USER_LOGGED;
    }

    @Nested
    @DisplayName("Checking the correct work of the valiedation methods")
    class CheckingFormat {
        @DisplayName("Given valid email format the correct format of the email")
        @Test
        void checkMailTest() throws ParseException {
            Assertions.assertThat(validations.checkEmail(userDto))
                    .isTrue();
        }



        @DisplayName("Checking the correct format of the password")
        @Test
        void checkPasswordTest() throws ParseException {
            Assertions.assertThat(validations.checkPassword(userDto))
                    .isTrue();
        }

        @DisplayName("Checking the correct format of the date")
        @Test
        void checkDateFormatTest() throws ParseException {
            Assertions.assertThat(validations.checkDateFormat(userDto))
                    .isTrue();
        }

        @DisplayName("Checking that the user is of legal age")
        @Test
        void checkAgeTest() throws ParseException {
            Assertions.assertThat(validations.checkAge(userDto))
                    .isTrue();
        }

        @DisplayName("Checking that the email exists in any register of the database")
        @Test
        void checkSameEmailTest() {
            UserService userService = mock(UserService.class);
            String userEmail = "test@example.com";
            when(userService.getUserByEmail(userEmail)).thenThrow(new RuntimeException("User not found"));
            UserDto userToCheck = UserDto.builder().email(userEmail).build();
            Validations validations = new Validations(userService);
            boolean result = validations.checkSameEmail(userToCheck);
            Assertions.assertThat(result).isFalse();
        }

        @DisplayName("Checking the last five methods all-in-one.")
        @Test
        void checkAllMethodsUserTest() throws ParseException {
            when(userService.getUserByEmail(userDto.getEmail())).thenReturn(userDto);
            Assertions.assertThat(validations.checkAllMethods(userDto))
                    .isTrue();
        }
    }

    @Nested
    @DisplayName("When the user try to change his/her data Then we check the email exists")
    class ExistsEmail {


        @Test
        @DisplayName("The email exists in the database, but it is himself.")
        void isExistsEmailAndNotIsFromTheUserFalseTest() {
            UserDto userToCheck = DataToUserControllerTesting.USER_ID_2;
            UserDto userDto = DataToUserControllerTesting.USER_ID_2;
            userDto.setEmail("newemail@gmail.com");

            boolean result = validations.isExistsEmailAndNotIsFromTheUser(userToCheck, userDto);
            Assertions.assertThat(result).isFalse();
        }

        @Test
        @DisplayName("The email exists in the database and it is not from himself.")
        void isExistsEmailAndNotIsFromTheUserTest() {
            UserDto userToCheck = DataToUserControllerTesting.USER_ID_2;
            userDto = DataToUserControllerTesting.USER_ID_3;

            boolean resultOnceModified = validations.isExistsEmailAndNotIsFromTheUser(userToCheck, userDto);
            Assertions.assertThat(resultOnceModified).isTrue();
        }
    }
}