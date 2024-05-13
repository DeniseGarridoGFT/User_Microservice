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


    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        addressService = mock(AddressService.class);
        initializerController = new InitializerController(userService,addressService);

    }

    @Test
    public void testAddUser() throws ParseException {
        when(addressService.addAddress(DataInitzializerController.ADDRESS_VALLECAS_WITHOUT_ID))
                .thenReturn(DataInitzializerController.ADDRESS_VALLECAS);

        when(userService.addUser(DataInitzializerController.USER_WITHOUT_ID))
                .thenReturn(DataInitzializerController.USER_LOGGED);
        UserDto userDtoTest = DataInitzializerController.USER_WITHOUT_ID;

        ResponseEntity<UserDto> responseInvalidEmail = initializerController.addUser(userDtoTest);
//        assertEquals("denise@gmail.com",userInvalidEmail.getEmail());
        assertEquals(HttpStatus.CREATED, responseInvalidEmail.getStatusCode());
//        assertEquals("password", userDtoTest.getPassword());
    }

    @Nested
    @DisplayName("When try to login user")
    class LogginTest{
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

            verify(userService,times(1)).getUserByEmail(anyString());

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
            }catch (Exception exception){
                //Then
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(responseStatusException.getMessage()).isEqualTo("401 UNAUTHORIZED \"Email or password is incorrect\"");
            }

            verify(userService,times(1)).getUserByEmail(anyString());
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
            }catch (Exception exception){
                //Then
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(responseStatusException.getMessage()).isEqualTo("401 UNAUTHORIZED \"Email or password is incorrect\"");
            }

            verify(userService,times(1)).getUserByEmail(anyString());
        }
    }
}