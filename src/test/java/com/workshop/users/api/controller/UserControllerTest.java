package com.workshop.users.api.controller;

import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.*;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

//import static com.workshop.users.api.controller.Data.DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS;
//import static com.workshop.users.api.controller.Data.DataToUserControllerTesting.USER_ID_2;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    private UserService userService;
    private UserController userController;
    private AddressService addressService;
    private UserDto userDtoChecked;

    private Validations validations;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        validations = mock(Validations.class);
        userService  = Mockito.mock(UserService.class);
        addressService = Mockito.mock(AddressService.class);
        userController = new UserController(userService, addressService, validations);
        userDtoChecked = UserDto.builder()
                .id(DataToUserControllerTesting.USER_ID_2.getId())
                .name(DataToUserControllerTesting.USER_ID_2.getName())
                .email(DataToUserControllerTesting.USER_ID_2.getEmail())
                .lastName(DataToUserControllerTesting.USER_ID_2.getLastName())
                .fidelityPoints(DataToUserControllerTesting.USER_ID_2.getFidelityPoints())
                .phone(DataToUserControllerTesting.USER_ID_2.getPhone())
                .birthDate(DataToUserControllerTesting.USER_ID_2.getBirthDate())
                .address(DataToUserControllerTesting.USER_ID_2.getAddress())
                .country(DataToUserControllerTesting.USER_ID_2.getCountry())
                .build();
        addressDto = AddressDto.builder()
                .id(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getId())
                .cityName(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getCityName())
                .zipCode(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getZipCode())
                .street(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getStreet())
                .number(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getNumber())
                .door(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS.getDoor())
                .build();

    }





    @Order(1)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Checking get method")
    class Get {

        @DisplayName("Checking the correct functioning of get method")
        @Order(1)
        @Test
        void getUser() throws NotFoundUserException {

            when(userService.getUserById(2L)).thenReturn(userDtoChecked);
            ResponseEntity<UserDto> responseEntity = userController.getUser(2L);
            UserDto userDto = responseEntity.getBody();
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals("Denise", userDto.getName());
            assertNotEquals("Tarraga", userDto.getLastName());
            assertEquals("denise@gmail.com", userDto.getEmail());
            assertEquals("C/VarajasNavaja", userDto.getAddress().getStreet());
            assertNotEquals("41458", userDto.getPhone());
            assertNotEquals("123456789", userDto.getPassword());
            assertEquals(100, userDto.getFidelityPoints());
        }
        @DisplayName("Checking the correct functioning of get method Then throw an exception")
        @Order(2)
        @Test
        void getUserNotFoundExceptions() throws NotFoundUserException {
            when(userService.getUserById(2L)).thenThrow(new NotFoundUserException("Can't found user with this id"));
            assertThatThrownBy(()->userController.getUser(2L))
                    .isInstanceOf(NotFoundUserException.class);
        }
    }
    @Order(2)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("When try to update a user")
    class PutTest {
        @Test
        @Order(1)
        @DisplayName("Given a valid user then update the user correctly")
        void putMappingTest() throws Exception {

            userDtoChecked.setEmail("paquito@gmail.com");
//            AddressDto addressDto = DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS;

            when(validations.checkAllMethods(userDtoChecked)).thenReturn(true);
            when(userService.updateUser(2L, userDtoChecked)).thenReturn(userDtoChecked);

            ResponseEntity<UserDto> responseEntity = userController.updateUser(2L, userDtoChecked);
            UserDto userResponse = responseEntity.getBody();

            assertThat(userResponse).isEqualTo(userDtoChecked);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getHeaders()).isEmpty();
            verify(validations, times(1)).checkAllMethods(userDtoChecked);
            verify(userService, times(1)).updateUser(userDtoChecked.getId(), userDtoChecked);
        }

        @Test
        @Order(2)
        @DisplayName("Given an existing user with incorrect values Then return the BAD_REQUEST ")
        void updateUserErrorPasswordTest() throws ResponseStatusException {
            userDtoChecked.setPassword("wArong@.com");

            when(validations.checkAllMethods(DataToUserControllerTesting.USER_ID_2))
                    .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password must contain, at least, 8 alphanumeric characters, uppercase, lowercase and special character."));

            assertThatThrownBy(() -> userController.updateUser(2L, userDtoChecked))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("The password must contain, at least, 8 alphanumeric " +
                            "characters, uppercase, lowercase and special character.")
                    .extracting(throwable -> (ResponseStatusException) throwable)
                    .satisfies(exception -> {
                        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    });
        }
        @Test
        @Order(3)
        @DisplayName("Given an non associated address Then return the NOT_FOUND exception ")
        void updateUserErrorNotFoundAddress() throws Exception {
            when(validations.checkAllMethods(userDtoChecked))
                    .thenReturn(true);
            when(addressService.updateAddress(anyLong(), any(AddressDto.class))).
                    thenThrow(new AddressNotFoundException("The address was not found"));
            when(userService.updateUser(anyLong(), any(UserDto.class))).
                    thenReturn(userDtoChecked);
            UserDto userDto = userDtoChecked;

            assertThatThrownBy(() ->  userController.updateUser(2L,userDto))
                    .isInstanceOf(AddressNotFoundException.class);

        }

        @Test
        @Order(4)
        @DisplayName("Given an non associated user Then return the NOT_FOUND exception ")
        void updateUserErrorNotFoundUser() throws Exception {
            when(validations.checkAllMethods(userDtoChecked))
                    .thenReturn(true);
            when(addressService.updateAddress(userDtoChecked.getAddress().getId(),userDtoChecked.getAddress())).thenReturn(userDtoChecked.getAddress());
            when(userService.updateUser(userDtoChecked.getId(),userDtoChecked)).thenThrow(new UserNotFoundException("The user was not found"));
            UserDto userDto = userDtoChecked;

            assertThatThrownBy(() -> userController.updateUser(2L, userDto))
                    .isInstanceOf(UserNotFoundException.class);

        }



    }

}