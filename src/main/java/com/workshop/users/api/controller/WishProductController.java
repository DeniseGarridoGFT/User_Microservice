package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.ProductNotFoundException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.exceptions.WishProductNotFoundException;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @Transactional(rollbackFor = ConflictWishListException.class)
    public ResponseEntity<WishListDto> postWishList(@Validated @RequestBody WishListDto wishListDto)
            throws NotFoundUserException, ProductNotFoundException, ConflictWishListException {
        ValidationsWishList.validateUserId(wishListDto,userService);
        ValidationsWishList.validateExistsProduct(wishListDto,productService);
        ValidationsWishList.saveWishList(wishListDto,wishProductService);
        return new ResponseEntity<>(wishListDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/wishlist/{user_id}/{product_id}")
    @Transactional(rollbackFor = WishProductNotFoundException.class)
    public ResponseEntity<WishListDto> deleteWishList(@Validated @PathVariable(name = "user_id") Long userId,
                                                      @PathVariable(name = "product_id") Long productId)
            throws WishProductNotFoundException {
        wishProductService.deleteWishProducts(WishListDto.getEntity(userId,productId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
