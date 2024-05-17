package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public class ValidationsWishList {
    private ValidationsWishList(){

    }

    public static void validateExistsProduct(WishListDto wishListDto, ProductService productService)
            throws ResponseStatusException {
        Set<Long> productIds = wishListDto.getProductsIds();
        for (Long productId:productIds){
            productService.findProductById(productId);
        }
    }

    public static void validateUserId(WishListDto wishListDto, UserService userService)
            throws ResponseStatusException{
        try {
            userService.getUserById(wishListDto.getUserId());
        }catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }


}
