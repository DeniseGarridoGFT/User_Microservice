package com.workshop.users.services.product;

import com.workshop.users.api.dto.Product;
import com.workshop.users.repositories.ProductRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
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
    }


    @Nested
    @DisplayName("When find product by Id")
    class FindProductById{

        @Test
        @DisplayName("Given a valid Id Then return the associated Product")
        void findProductByIdGood() {
            //Given
            Mockito.when(productRepository.findProductById(2L)).thenReturn(Mono.just(product));
            //When
            Product productObtainedCallingMethod = productService.findProductById(2L);
            //Then
            assertThat(productObtainedCallingMethod).isEqualTo(product);
        }
        @Test
        @DisplayName("Given a invalid Id Then return the associated Product")
        void findProductByIdInvalid() {
            //Given
            Mockito.when(productRepository.findProductById(2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists"));
            //When
            try {
                productService.findProductById(2L);
            }catch (Exception exc){
                //Then
                assertThat(exc).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exc;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException.getMessage()).isEqualTo("404 NOT_FOUND \"The product not exists\"");
            }
        }
        @Test
        @DisplayName("Given a null Then throw exception")
        void findProductByIdNull() {
            //Given
            Mockito.when(productRepository.findProductById(2L)).thenReturn(Mono.empty());
            //When
            try {
                productService.findProductById(2L);
            }catch (Exception exc){
                //Then
                assertThat(exc).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exc;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException.getMessage()).isEqualTo("404 NOT_FOUND \"The product not exists\"");
            }
        }
    }



}