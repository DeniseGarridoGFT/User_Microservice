package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


class ProductRepositoryImplTest {


    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
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
        productRepository =  new ProductRepositoryImpl();
    }

    @Nested
    @DisplayName("When findProductById")
    class FindUserById{
        @Test
        @DisplayName("Given a good id then return a Mono<Product> product ")
        void findProductById() {
            Mono<Product> productMono = productRepository.findProductById(2L);
            assertThat(productMono.block()).isEqualTo(product);
        }

        @Test
        @DisplayName("Given a bad id then throw ResponseEntityError ")
        void findProductByIdBadId() {
            try {
                Mono<Product> productMono = productRepository.findProductById(99999999L);
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException.getMessage()).isEqualTo("404 NOT_FOUND \"The product Id is not valid\"");
            }
        }
    }

}