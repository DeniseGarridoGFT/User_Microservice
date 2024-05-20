package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.MyResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestIntegrationLoginTest {

    @Autowired
    private WebTestClient webTestClient;


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
