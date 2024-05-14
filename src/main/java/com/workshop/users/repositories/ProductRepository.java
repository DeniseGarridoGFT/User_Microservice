package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> findProductById(Long id) throws ResponseStatusException;
}
