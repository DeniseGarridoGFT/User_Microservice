package com.workshop.users.services.product;

import com.workshop.users.api.dto.Product;
import com.workshop.users.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    List<Product> findProductsByIds(List<Long> ids) throws ProductNotFoundException;

}
