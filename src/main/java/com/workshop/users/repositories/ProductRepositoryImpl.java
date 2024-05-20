package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final RestClient restClient;

    public ProductRepositoryImpl(RestClient.Builder builder) {
        this.restClient =  builder.build();
    }


    @Override
    public List<Product> findProductsByIds(List<Long> ids) {
        return List.of(Objects.requireNonNull(restClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(ids)
                .retrieve()
                .body(Product[].class)));
    }
}
