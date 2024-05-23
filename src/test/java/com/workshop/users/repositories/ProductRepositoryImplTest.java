package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import com.workshop.users.exceptions.NotFoundProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {


    private Product[] products;
    private RestClient.Builder restClientBuilder;
    private RestClient restClient;
    private List<Long> ids;
    private ProductRepository productRepository;
    private RestClient.RequestBodyUriSpec request;

    private RestClient.ResponseSpec response;


    @BeforeEach
    void setUp() {
        products = List.of(Product.builder()
                        .id(2L)
                        .name("ToyStory toy")
                        .categoryId(3)
                        .currentStock(200)
                        .description("The best toy for your children")
                        .price(10D)
                        .minStock(100)
                        .weight(0.5D)
                        .build(),
                Product.builder()
                        .id(1L)
                        .name("ToyStory toy")
                        .categoryId(3)
                        .currentStock(200)
                        .description("The best toy for your children")
                        .price(10D)
                        .minStock(100)
                        .weight(0.5D).build()).toArray(new Product[0]);

        ids = List.of(2L,1L);
        restClientBuilder = Mockito.mock(RestClient.Builder.class);
        restClient = Mockito.mock(RestClient.class);
        request = Mockito.mock(RestClient.RequestBodyUriSpec.class);
        response = Mockito.mock(RestClient.ResponseSpec.class);

        when(restClientBuilder.build()).thenReturn(restClient);

        productRepository = new ProductRepositoryImpl(restClientBuilder);

    }

    @Nested
    @DisplayName("When findProductById")
    class FindUserById {
        @Test
        @DisplayName("Given a good id then return a List product of products ")
        void findProductById() {
            //Given
            when(restClient.post()).thenReturn(request);
            Mockito.when(request.uri(anyString())).thenReturn(request);
            Mockito.when(request.contentType(Mockito.any())).thenReturn(request);
            Mockito.when(request.body(ids)).thenReturn(request);
            Mockito.when(request.retrieve()).thenReturn(response);
           when(response.onStatus(
                    Mockito.any(),
                    Mockito.any()
            )).thenReturn(response);
            Mockito.when(response.body(Product[].class)).thenReturn(products);
            //When
            List<Product> result = productRepository.findProductsByIds(ids);
            //Then
            assertThat(result).isNotNull().isEqualTo(List.of(products)).hasSize(2);



        }

        @Test
        @DisplayName("Given a bad id then throw ResponseEntityError ")
        void findProductByIdBadId() {
            //Given
            when(restClient.post()).thenReturn(request);
            Mockito.when(request.uri(anyString())).thenReturn(request);
            Mockito.when(request.contentType(Mockito.any())).thenReturn(request);
            Mockito.when(request.body(ids)).thenReturn(request);
            Mockito.when(request.retrieve()).thenReturn(response);
            when(response.onStatus(
                    Mockito.any(),
                    Mockito.any()
            )).thenThrow(new NotFoundProductException("Can't found one id of one product"));
            //When and Then
            assertThatThrownBy(() -> {
                productRepository.findProductsByIds(ids);
            }).isInstanceOf(NotFoundProductException.class)
                    .hasFieldOrPropertyWithValue("message", "Can't found one id of one product");
        }
    }
}
