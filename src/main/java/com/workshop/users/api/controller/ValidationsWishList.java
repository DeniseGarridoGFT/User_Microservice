package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.ProductNotFoundException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;

public class ValidationsWishList {


    public static void validateExistsProduct(WishListDto wishListDto, ProductService productService)
            throws ProductNotFoundException {
        productService.findProductsByIds(wishListDto.getProductsIds().stream().toList());
    }

    public static void validateUserId(WishListDto wishListDto, UserService userService)
            throws NotFoundUserException {
        userService.getUserById(wishListDto.getUserId());
    }

    public static void saveWishList(WishListDto wishListDto,WishProductService wishProductService)
            throws ConflictWishListException {
        for (Long productId:wishListDto.getProductsIds()){
            wishProductService.addWishProducts(WishListDto.getEntity(wishListDto.getUserId(),productId));
        }
    }

}
