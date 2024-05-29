package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;
import com.workshop.users.exceptions.NotFoundProductException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.List;

public interface ProductRepository {
    @Retryable(retryFor = NotFoundProductException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    List<Product> findProductsByIds(List<Long> ids) throws NotFoundProductException;
}
