package com.workshop.users.repositories;

import com.workshop.users.api.dto.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findProductsByIds(List<Long> ids);
}
