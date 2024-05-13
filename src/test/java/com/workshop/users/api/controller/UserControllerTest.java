package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private int port;

    @BeforeEach
    void setUp() {
        userService  = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void getUser() {
        when(userService.getUserById(2L)).thenReturn(DataToUserControllerTesting.USER_ID_2);
        ResponseEntity<UserDto> responseEntity = userController.getUser(2L);
        UserDto userDto = responseEntity.getBody();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals("Denise",userDto.getName());
        assertNotEquals("Tarraga",userDto.getLastName());
        assertEquals("denise@gmail.com",userDto.getEmail());
        assertEquals("C/VarajasNavaja", userDto.getAddress().getStreet());
        assertNotEquals("41458", userDto.getPhone());
        assertNotEquals("123456789",userDto.getPassword());
        assertEquals(100, userDto.getFidelityPoints());
    }



    @Test
    void testPutMapping() throws ParseException {
        port = 8080;
        when(userService.getUserById(2L))
                .thenReturn(DataToUserControllerTesting.USER_ID_2);
        when(userService.addUser(DataToUserControllerTesting.USER_ID_2_MODIFIED)).thenReturn(DataToUserControllerTesting.USER_ID_2_MODIFIED);

        ResponseEntity<UserDto> responseEntity = userController.updateUser(2L,DataToUserControllerTesting.USER_ID_2_MODIFIED);

        assertThat(responseEntity.getBody()).isEqualTo(DataToUserControllerTesting.USER_ID_2_MODIFIED);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders()).hasSize(0);
        assertThat(responseEntity.getBody().checkOver18()).isTrue();

    }

}