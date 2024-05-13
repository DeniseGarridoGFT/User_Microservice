package com.workshop.users.api.controller;

import com.workshop.users.api.dto.*;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerAddUserTest {


    private UserService userService;
    private AddressService addressService;
    private InitializerController initializerController;
    private AddressDto addressDto;
    private CountryDto countryDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        addressService = mock(AddressService.class);
        initializerController = new InitializerController(userService,addressService);
        addressDto = AddressDto.builder()
                .id(3L)
                .cityName("Madrid")
                .street("C/VarajasNavaja")
                .zipCode("43567")
                .number(3)
                .door("1A")
                .build();

        countryDto = CountryDto.builder()
                .id(1L)
                .name("España")
                .tax(21F)
                .prefix("+34")
                .timeZone("Europe/Madrid")
                .build();

        userDto = UserDto.builder()
                .id(2L)
                .name("Denise")
                .lastName("Garrido")
                .email("denise@gmail.com")
                .address(addressDto)
                .birthDate("2004/14/04")
                .password("password")
                .phone("123456789")
                .fidelityPoints(100)
                .country(countryDto)
                .build();
    }

    @Test
    public void testAddUser() throws ParseException {
        when(userService.addUser(userDto)).thenReturn(userDto);
        ResponseEntity<UserDto> response = initializerController.addUser(userDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

//    @Test
//    public void testGetUser() {
//        Long userId = 1L;
//        User user = new User(userId, "Denise", "Garrido");
//
//        when(userService.getUserById(userId)).thenReturn(user);
//
//        ResponseEntity<UserEntity> response = initializerController.getUser(userId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
}
