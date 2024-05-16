package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryImplTest {

    private ProductRepository productRepository;
    private Product product;
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    private WebClient.ResponseSpec responseSpec;


    @BeforeEach
    void setUp() {
        webClientBuilder = Mockito.mock(WebClient.Builder.class);
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
        webClient = Mockito.mock(WebClient.class);
        requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);

        productRepository = new ProductRepositoryImpl(webClientBuilder);
    }

    @Nested
    @DisplayName("When findProductById")
    class FindUserById {
        @Test
        @DisplayName("Given a good id then return a Mono<Product> product ")
        void findProductById() {
            Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
            Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString(), Mockito.anyLong())).thenReturn(requestHeadersUriSpec);
            Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
            Mockito.when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(product));

            StepVerifier.create(productRepository.findProductById(2L))
                    .expectNext(product)
                    .verifyComplete();
        }

        @Test
        @DisplayName("Given a bad id then throw ResponseEntityError ")
        void findProductByIdBadId() {
            WebClient.ResponseSpec errorResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);

            Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
            Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString(), Mockito.anyLong())).thenReturn(requestHeadersUriSpec);
            Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(errorResponseSpec);
            Mockito.when(errorResponseSpec.bodyToMono(Product.class))
                    .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists")));

            StepVerifier.create(productRepository.findProductById(99999999L))
                    .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                            ((ResponseStatusException) throwable).getStatusCode().equals(HttpStatus.NOT_FOUND))
                    .verify();
        }
    }
}
