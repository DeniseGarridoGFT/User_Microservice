package com.workshop.users.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.users.api.dto.Product;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.repositories.ProductRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class End2EndWishListTest {

    private ObjectMapper objectMapper;
    private Product product;

    private static MockWebServer mockBackEnd;

    private WishListDto wishListDto;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        product = Product.builder()
                .id(1L)
                .weight(25D)
                .name("ToyStory Toy")
                .minStock(100)
                .price(23D)
                .currentStock(200)
                .description("The best toy for your children!")
                .categoryId(2)
                .build();

        wishListDto = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(1L,2L,3L))).build();

    }

    @BeforeAll
    static void beforeAll() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8081);
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockBackEnd.close();
    }

    @Nested
    @DisplayName("When post wish products")
    class PostWishList{
        @Test
        void postWishList() throws JsonProcessingException {
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(objectMapper.writeValueAsString(product))
                    .addHeader("Content-Type","application/json"));
            webTestClient.post()
                    .uri("/wishlist")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(wishListDto)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(WishListDto.class)
                    .value(userDto -> {
                        //Then
                        assertThat(userDto).isEqualTo(wishListDto);
                    });
        }
    }


}
