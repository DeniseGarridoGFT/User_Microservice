package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final WebClient webClient;

    public ProductRepositoryImpl(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public Mono<Product> findProductById(Long id) throws ResponseStatusException {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class).onErrorMap(throwable -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists");
                });
    }
}
