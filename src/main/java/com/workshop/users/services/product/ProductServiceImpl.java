package com.workshop.users.services.product;

import com.workshop.users.api.dto.Product;
import com.workshop.users.exceptions.ProductNotFoundException;
import com.workshop.users.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findProductsByIds(List<Long> ids) throws ProductNotFoundException {
        try{
            return productRepository.findProductsByIds(ids);
        }catch (Exception exception){
            throw new ProductNotFoundException("Not found any product");
        }
    }
}
