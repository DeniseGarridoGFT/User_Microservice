package com.workshop.users.services.product;

import com.workshop.users.api.dto.Product;
import reactor.core.publisher.Mono;

public interface ProductService {

    Product findProductById(Long id);

}
