package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataInitzializerController;
import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.country.CountryService;
import com.workshop.users.services.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationTest {

    private UserService userService;
    private AddressService addressService;

    private CountryService countryService;
    private InitializerController initializerController;
    private AddressDto addressDto;
    private CountryDto countryDto;
    private UserDto userDto;
    private Validations validations;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        addressService = mock(AddressService.class);
        validations = new Validations(userService);
        countryService = mock(CountryService.class);
        initializerController = new InitializerController(userService, addressService,validations, countryService);
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

    @Nested
    @DisplayName("Checking the incorrect work of the valiedation methods")
    class CheckingInvalidFormat {
        @Test
        @DisplayName("Checking the incorrect format of the email")
        void testEmailFormatInvalid(){
            UserDto userToCheck = DataInitzializerController.INVALID_USER;
            ResponseStatusException responseStatusException = null;
            try {
                validations.checkEmail(userToCheck);
            }catch (ResponseStatusException exception){
                responseStatusException = exception;
            }
            Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(responseStatusException.getReason()).isEqualTo("Invalid email format.");
        }

        @Test
        @DisplayName("Checking the incorrect format of the password")
        void testPasswordFormatInvalid(){
            UserDto userToCheck = DataInitzializerController.INVALID_USER;
            ResponseStatusException responseStatusException = null;
            try {
                validations.checkPassword(userToCheck);
            }catch (ResponseStatusException exception){
                responseStatusException = exception;
            }
            Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(responseStatusException.getReason()).isEqualTo("The password must contain, at least, 8 alphanumeric characters, uppercase, lowercase an special character.");
        }

        @Test
        @DisplayName("Checking the incorrect format of the birth date")
        void testBirthDateFormatInvalid(){
            UserDto userToCheck = DataInitzializerController.INVALID_USER;
            ResponseStatusException responseStatusException = null;
            try {
                validations.checkDateFormat(userToCheck);
            }catch (ResponseStatusException exception){
                responseStatusException = exception;
            }
            Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(responseStatusException.getReason()).isEqualTo("The format of the birth date is not valid.");
        }

        @Test
        @DisplayName("Checking the incorrect format of the phone")
        void testPhoneFormatInvalid(){
            UserDto userToCheck = DataInitzializerController.INVALID_USER;
            ResponseStatusException responseStatusException = null;
            try {
                validations.checkPhone(userToCheck);
            }catch (ResponseStatusException exception){
                responseStatusException = exception;
            }
            Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(responseStatusException.getReason()).isEqualTo("The phone must contain 9 numeric characters.");
        }
        @Test
        @DisplayName("Checking the user is under 18")
        void testIllegalAgeFormatInvalid(){
            UserDto userToCheck = DataInitzializerController.INVALID_USER;
            ResponseStatusException responseStatusException = null;
            try {
                validations.checkAge(userToCheck);
            }catch (ResponseStatusException exception){
                responseStatusException = exception;
            }
            Assertions.assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            Assertions.assertThat(responseStatusException.getReason()).isEqualTo("The user must be of legal age.");
        }
    }
}