package com.workshop.users.services.product;

import com.workshop.users.api.dto.Product;
import com.workshop.users.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product findProductById(Long id) throws ResponseStatusException {
        Product product = productRepository.findProductById(id).block();
        if (product==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists");
        }
        return product;
    }
}
