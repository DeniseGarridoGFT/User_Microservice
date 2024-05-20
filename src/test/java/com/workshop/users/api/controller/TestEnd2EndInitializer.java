package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.MyResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class TestEnd2EndRegisterTest {
    @Autowired
    private WebTestClient webTestClient;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Nested
    @DisplayName("Register")
    class Register {

        @Test
        @DisplayName("Given correct credentials a user is registered. Then return that user")
        void registerUser() {
            // Given
            UserDto newUser = UserDto.builder()
                    .name("Aria")
                    .lastName("Fei")
                    .email("aria@example.com")
                    .password("Ar1a@31234.")
                    .fidelityPoints(40)
                    .birthDate("1994/04/14")
                    .phone("123456789")
                    .address(AddressDto.builder()
                            .cityName("Valencia")
                            .zipCode("46360")
                            .street("C/ La Calle")
                            .number(32)
                            .door("2A")
                            .build())
                    .country(CountryDto.builder()
                            .name("España")
                            .tax(21f)
                            .prefix("+34")
                            .timeZone("Europe/Madrid")
                            .build())
                    .build();

            // When
            webTestClient.post()
                    .uri("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(newUser)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(UserDto.class)
                    .value(userDto -> {
                        // Then
                        assertThat(passwordEncoder.matches(newUser.getPassword(), userDto.getPassword())).isTrue();
                        assertThat(userDto.getName()).isEqualTo(newUser.getName());
                        assertThat(userDto.getLastName()).isEqualTo(newUser.getLastName());
                        assertThat(userDto.getEmail()).isEqualTo(newUser.getEmail());
                        assertThat(userDto.getBirthDate()).isEqualTo(newUser.getBirthDate());
                        assertThat(userDto.getFidelityPoints()).isEqualTo(newUser.getFidelityPoints());
                        assertThat(userDto.getPhone()).isEqualTo(newUser.getPhone());

                        assertThat(userDto.getAddress().getCityName()).isEqualTo(newUser.getAddress().getCityName());
                        assertThat(userDto.getAddress().getZipCode()).isEqualTo(newUser.getAddress().getZipCode());
                        assertThat(userDto.getAddress().getStreet()).isEqualTo(newUser.getAddress().getStreet());
                        assertThat(userDto.getAddress().getNumber()).isEqualTo(newUser.getAddress().getNumber());
                        assertThat(userDto.getAddress().getDoor()).isEqualTo(newUser.getAddress().getDoor());

                        assertThat(userDto.getCountry().getName()).isEqualTo(newUser.getCountry().getName());
                        assertThat(userDto.getCountry().getTax()).isEqualTo(newUser.getCountry().getTax());
                        assertThat(userDto.getCountry().getPrefix()).isEqualTo(newUser.getCountry().getPrefix());
                        assertThat(userDto.getCountry().getTimeZone()).isEqualTo(newUser.getCountry().getTimeZone());
                    });
        }

        @Test
        @DisplayName("Given incorrect credentials a user can't be registered. Then return BAD_REQUEST status")
        void invalidUserRegister() {
            //Given
            UserDto invalidUser = UserDto.builder()
                    .name("Aria")
                    .lastName("Fei")
                    .email("ariaexample.com")
                    .password("Ar1a@31234.")
                    .fidelityPoints(40)
                    .birthDate("1994/04/14")
                    .phone("123456789")
                    .address(AddressDto.builder()
                            .cityName("Valencia")
                            .zipCode("46360")
                            .street("C/ La Calle")
                            .number(32)
                            .door("2A")
                            .build())
                    .country(CountryDto.builder()
                            .name("España")
                            .tax(21f)
                            .prefix("+34")
                            .timeZone("Europe/Madrid")
                            .build())
                    .build();
            //When
            webTestClient.post()
                    .uri("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(invalidUser)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }


    @Nested
    @DisplayName("Login")
    class TestEnd2EndLoginTest {


        @Test
        @DisplayName("Given an logged user when call to login endpoint Then return associated user")
        void postLogin() {
            //Given
            UserDto expectedUserDto = UserDto.builder()
                    .id(1L)
                    .name("Juan")
                    .lastName("García")
                    .email("juangarcia@example.com")
                    .password("$2a$10$OyJUHBSm0sU8eF8os0ZuoOwDRmgg8ns4owWtIXItlYmN.1pDVxve6")
                    .fidelityPoints(100)
                    .birthDate("1990/01/01")
                    .phone("123456789")
                    .address(AddressDto.builder()
                            .id(1L)
                            .cityName("Madrid")
                            .zipCode("47562")
                            .street("C/ La Coma")
                            .number(32)
                            .door("1A")
                            .build())
                    .country(CountryDto.builder()
                            .id(1L)
                            .name("España")
                            .tax(21f)
                            .prefix("+34")
                            .timeZone("Europe/Madrid")
                            .build())
                    .build();
            Login validLogin = Login.builder()
                    .email("juangarcia@example.com")
                    .password("1234")
                    .build();
            //When
            webTestClient.post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(validLogin)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(UserDto.class)
                    .value(userDto -> {
                        //Then
                        assertThat(userDto).isEqualTo(expectedUserDto);
                    });
        }

        @Test
        @DisplayName("Given an bad email user when call to login endpoint Then return uauthorized status")
        void postLoginIncorrectEmail() {
            //Given
            Login validLogin = Login.builder()
                    .email("juangarcio@example.com")
                    .password("1234")
                    .build();
            //When
            webTestClient.post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(validLogin)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(MyResponseException.class)
                    .value(exception -> {
                        //Then
                        assertThat(exception).isEqualTo(MyResponseException.builder()
                                .code(HttpStatus.NOT_FOUND)
                                .message("The email or the password are incorrect.")
                                .build());
                    });
        }

        @Test
        @DisplayName("Given an bad password user when call to login endpoint Then return uauthorized status")
        void postLoginIncorrectPassword() {
            //Given
            Login validLogin = Login.builder()
                    .email("juangarcia@example.com")
                    .password("1235744")
                    .build();
            //When
            webTestClient.post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(validLogin)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(MyResponseException.class)
                    .value(exception -> {
                        //Then
                        assertThat(exception).isEqualTo(MyResponseException.builder()
                                .code(HttpStatus.NOT_FOUND)
                                .message("The email or the password are incorrect.")
                                .build());
                    });
        }
    }

    @Nested
    @DisplayName("Get user by Id")
    class TestEnd2EndGetUserTest {


        @Test
        @DisplayName("Given an  associated user id when call to get users endpoint Then return the correct user")
        void getUserById() {

            //When
            webTestClient.get()
                    .uri("/users/5")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(MyResponseException.class)
                    .value(exception -> {
                        //Then
                        assertThat(exception).isEqualTo(MyResponseException.builder()
                                .code(HttpStatus.NOT_FOUND)
                                .message("The user with this id don't exists.")
                                .build());
                    });
        }


        @Test
        @DisplayName("Given a non associated user id when call to get users endpoint Then not found exceptions")
        void getUserByIdNonAssociatedId() {

            //When
            webTestClient.get()
                    .uri("/users/5")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(MyResponseException.class)
                    .value(exception -> {
                        //Then
                        assertThat(exception).isEqualTo(MyResponseException.builder()
                                .code(HttpStatus.NOT_FOUND)
                                .message("The user with this id don't exists.")
                                .build());
                    });
        }

    }
}
