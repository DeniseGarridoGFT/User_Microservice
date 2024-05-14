package com.workshop.users.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.users.api.dto.Product;
import static org.assertj.core.api.Assertions.*;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;


class ProductRepositoryImplTest {


    private ObjectMapper objectMapper;
    private ProductRepository productRepository;
    private Product product;
    public static MockWebServer mockBackEnd;
    @BeforeAll
    static void beforeAll() throws IOException {

        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        product = Product.builder()
                .id(2L)
                .name("ToyStory toy")
                .categoryId(3)
                .currentStock(200)
                .description("The best toy for your children")
                .price(10D)
                .minStock(100)
                .weight(0.5D)
                .build();
        productRepository =  new ProductRepositoryImpl("http://localhost:"+mockBackEnd.getPort());
    }

    @Nested
    @DisplayName("When findProductById")
    class FindUserById{
        @Test
        @DisplayName("Given a good id then return a Mono<Product> product ")
        void findProductById() throws JsonProcessingException {
            //Given
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(objectMapper.writeValueAsString(product))
                    .addHeader("Content-Type", "application/json"));
            //When
            Mono<Product> productMono = productRepository.findProductById(2L);
            //Then
            assertThat(productMono.block()).isEqualTo(product);
        }

        @Test
        @DisplayName("Given a bad id then throw ResponseEntityError ")
        void findProductByIdBadId() {
            try {
                //When
                Mono<Product> productMono = productRepository.findProductById(99999999L);
            }catch (ResponseStatusException exception){
                //Then
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException.getMessage()).isEqualTo("404 NOT_FOUND \"The product Id is not valid\"");
            }
        }
    }

}