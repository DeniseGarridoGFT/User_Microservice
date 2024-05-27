package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.NotFoundProductException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.exceptions.NotFoundWishProductException;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wishlist")
public class WishProductController {

    private final WishProductService wishProductService;
    private final UserService userService;
    private final ProductService productService;

    public WishProductController(WishProductService wishProductService, UserService userService, ProductService productService) {
        this.wishProductService = wishProductService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping()
    @Transactional(rollbackFor = ConflictWishListException.class)
    public ResponseEntity<WishListDto> postWishList(@Validated @RequestBody WishListDto wishListDto)
            throws NotFoundUserException, NotFoundProductException, ConflictWishListException {
        ValidationsWishList.validateUserId(wishListDto,userService);
        ValidationsWishList.validateExistsProduct(wishListDto,productService);
        ValidationsWishList.saveWishList(wishListDto,wishProductService);
        return new ResponseEntity<>(wishListDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{user_id}/{product_id}")
    public ResponseEntity<WishListDto> deleteWishList(@PathVariable(name = "user_id") Long userId,
                                                      @PathVariable(name = "product_id") Long productId)
            throws NotFoundWishProductException {
        wishProductService.deleteWishProducts(WishListDto.getEntity(userId,productId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
