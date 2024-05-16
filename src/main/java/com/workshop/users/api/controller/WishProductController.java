package com.workshop.users.api.controller;

import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WishProductController {

    private WishProductService wishProductService;
    private UserService userService;
    private ProductService productService;

    public WishProductController(WishProductService wishProductService, UserService userService, ProductService productService) {
        this.wishProductService = wishProductService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/wishlist")
    public ResponseEntity<WishListDto> postWishList(@Validated @RequestBody WishListDto wishListDto) throws ResponseStatusException {
        wishListDto.isEmpty();
        ValidationsWishList.validateUserId(wishListDto,userService);
        ValidationsWishList.validateExistsProduct(wishListDto,productService);
        WishListDto wishListDtoToResponse = wishProductService.addWishProducts(wishListDto);
        return new ResponseEntity<>(wishListDtoToResponse, HttpStatus.CREATED);
    }


}
