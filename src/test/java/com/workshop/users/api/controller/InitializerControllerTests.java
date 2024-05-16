package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataInitzializerController;
import com.workshop.users.api.dto.*;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InitializerControllerTests {


    private UserService userService;
    private AddressService addressService;
    private InitializerController initializerController;
    private AddressDto addressDto;
    private CountryDto countryDto;
    private UserDto userDto;
    private Validations validations;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        addressService = mock(AddressService.class);
        validations = mock(Validations.class);
        initializerController = new InitializerController(userService, addressService, validations);

    }

    @Nested
    @DisplayName("When a user try to register")
    class RegisterTest {
        @Test
        @DisplayName("Given correct credentials the user is created and registered")
        void testRegisterUser() throws ParseException {

            // Given
            UserDto userRegistered = DataInitzializerController.USER_REGISTERED;
            AddressDto addressDto = DataInitzializerController.ADDRESS_VALLECAS_WITHOUT_ID;

            when(validations.checkAllMethods(userRegistered)).thenReturn(true);
            when(addressService.addAddress(addressDto)).thenReturn(DataInitzializerController.ADDRESS_VALLECAS);
            when(userService.addUser(userRegistered)).thenReturn(userRegistered);

            // When
            ResponseEntity<UserDto> response;
            response = initializerController.addUser(userRegistered);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isEqualTo(userRegistered);
            verify(validations, times(1)).checkAllMethods(userRegistered);
            verify(userService, times(1)).addUser(userRegistered);
            verify(addressService, times(1)).addAddress(addressDto);
        }

        @Test
        @DisplayName("Given incorrect credentials the user can't be registered")
        void testRegisterUserIncorrect() {

            // Given
            UserDto userNotRegistered = DataInitzializerController.USER_WITHOUT_ID;
            when(validations.checkAllMethods(userNotRegistered))
                    .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password must" +
                            " contain, at least, 8 alphanumeric characters, uppercase, lowercase an special character."));
            try {
                // When
                ResponseEntity<UserDto> responseStatusException = initializerController.addUser(userNotRegistered);
            } catch (ResponseStatusException exception) {
                // Then
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseStatusException.getReason()).isEqualTo("The password must contain, at least, 8 alphanumeric characters, uppercase, lowercase an special character.");
            }
            verify(validations, times(1)).checkAllMethods(userNotRegistered);
        }

        @Test
        @DisplayName("The address can't be added to the user, so it gives server error")
        void testAddressUserIncorrect() throws ParseException {
            // Given
            UserDto userWithAddress = DataInitzializerController.USER_WITHOUT_ID;
            AddressDto addressDto = userWithAddress.getAddress();
            when(validations.checkAllMethods(userWithAddress)).thenReturn(true);
            when(addressService.addAddress(addressDto)).thenThrow(new RuntimeException());
            when(userService.addUser(userWithAddress)).thenReturn(userWithAddress);
            // When
            try {
                initializerController.addUser(userWithAddress);
            } catch (ResponseStatusException exception) {
                // Then
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                assertThat(exception.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR \"Internal Server Error\"");
                verify(addressService, times(1)).addAddress(addressDto);
            }
            verify(addressService,times(1)).addAddress(addressDto);
            verify(userService,times(0)).addUser(userWithAddress);
        }

        @Test
        @DisplayName("There's an error creating the user")
        void testAddUserIncorrect() throws ParseException {
            // Given
            UserDto createdUser = DataInitzializerController.USER_WITHOUT_ID;
            when(validations.checkAllMethods(createdUser)).thenReturn(true);
            when(addressService.addAddress(any())).thenThrow(new RuntimeException());
            when(userService.addUser(createdUser)).thenThrow(new RuntimeException());
            // When
            try {
                initializerController.addUser(createdUser);
            } catch (ResponseStatusException exception) {
                // Then
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                assertThat(exception.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR \"Internal Server Error\"");
            }
            verify(userService,times(0)).addUser(createdUser);
        }

        @Nested
        @DisplayName("When try to login user")
        class LogginTest {
            @Test
            @DisplayName("Given existing email and correct password Then return the correct user")
            void loginUser() {
                //Given
                when(userService.getUserByEmail("denise@gmail.com")).thenReturn(DataInitzializerController.USER_LOGGED);
                Login userToLogin = Login.builder().email("denise@gmail.com").password("1234").build();
                //When
                ResponseEntity<?> userLogged = initializerController.loginUser(userToLogin);

                //Then
                assertThat(userLogged).isNotNull();
                assertThat(userLogged.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(userLogged.getBody()).isEqualTo(DataInitzializerController.USER_LOGGED);

                verify(userService, times(1)).getUserByEmail(anyString());

            }


            @Test
            @DisplayName("Given existing email and incorrect password Then return the password error")
            void loginUserErrorPassword() {
                //Given
                when(userService.getUserByEmail("denise@gmail.com")).thenReturn(DataInitzializerController.USER_LOGGED);
                Login userToLogin = Login.builder().email("denise@gmail.com").password("3214").build();
                try {
                    //When
                    ResponseEntity<UserDto> responseStatusException = initializerController.loginUser(userToLogin);
                } catch (Exception exception) {
                    //Then
                    assertThat(exception).isInstanceOf(ResponseStatusException.class);
                    ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                    assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                    assertThat(responseStatusException.getMessage()).isEqualTo("401 UNAUTHORIZED \"Email or password is incorrect\"");
                }

                verify(userService, times(1)).getUserByEmail(anyString());
            }

            @Test
            @DisplayName("Given non-existing email and correct password Then return the email error")
            void loginUserErrorEmail() {
                //Given
                when(userService.getUserByEmail("denipse@gmail.com")).thenThrow(new RuntimeException());
                Login userToLogin = Login.builder().email("denipse@gmail.com").password("3214").build();

                try {
                    //When
                    ResponseEntity<UserDto> responseStatusException = initializerController.loginUser(userToLogin);
                } catch (Exception exception) {
                    //Then
                    assertThat(exception).isInstanceOf(ResponseStatusException.class);
                    ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                    assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                    assertThat(responseStatusException.getMessage()).isEqualTo("401 UNAUTHORIZED \"Email or password is incorrect\"");
                }

                verify(userService, times(1)).getUserByEmail(anyString());
            }
        }
    }
}
