package com.workshop.users.api.controller;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.NotFoundProductException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ValidationsWishListTest {

    private WishListDto wishListDto;
    private WishProductService wishProductService;
    private ProductService productService;
    private UserService userService;


    @BeforeEach
    void setUp() {
        wishListDto = WishListDto.builder()
                .userId(1L)
                .productsIds(new HashSet<>(List.of(1L,2L,3L)))
                .build();
        wishProductService = mock(WishProductService.class);
        productService = mock(ProductService.class);
        userService = mock(UserService.class);
    }

    @Nested
    @DisplayName("When try to validateUserId")
    class ValidateUserId{
        @DisplayName("Given valid userId Then don't have an error")
        @Test
        void validUserId() throws NotFoundUserException {
            when(userService.getUserById(1L))
                    .thenReturn(UserDto.builder().build());

            ValidationsWishList.validateUserId(wishListDto,userService);
        }
        @DisplayName("Given invalid userId Then throw an error")
        @Test
        void nonUserId() throws NotFoundUserException {
            when(userService.getUserById(1L))
                    .thenThrow(new NotFoundUserException("Not found user"));

            assertThatThrownBy(()->
                ValidationsWishList.validateUserId(wishListDto,userService))
            .isInstanceOf(NotFoundUserException.class);
        }
    }


    @Nested
    @DisplayName("When try to validate if the product that the user want to save in the wish list")
    class ValidateTheProductExists{
        @DisplayName("Given an existed products Then don't have an error")
        @Test
        void validateIfExitsProduct() throws NotFoundProductException {
            when(productService.findProductsByIds(wishListDto.getProductsIds().stream().toList()))
                    .thenReturn(anyList());
            ValidationsWishList.validateExistsProduct(wishListDto,productService);
        }

        @DisplayName("Given a non existed product Then throw error")
        @Test
        void nonProductId() throws NotFoundProductException {
            when(productService.findProductsByIds(wishListDto.getProductsIds().stream().toList()))
                    .thenThrow(new NotFoundProductException("Not found product"));

            assertThatThrownBy(()->
                ValidationsWishList.validateExistsProduct(wishListDto,productService))
            .isInstanceOf(NotFoundProductException.class);
        }
    }

    @Nested
    @DisplayName("When try to save products in the wish list")
    class SaveProduct{
        @DisplayName("Given an wish list dto Then save the all the products correctly")
        @Test
        void saveProductInWishList() throws ConflictWishListException {
            when(wishProductService.addWishProducts(any()))
                    .thenReturn(any());
            ValidationsWishList.saveWishList(wishListDto,wishProductService);
            verify(wishProductService,times(3)).addWishProducts(any());
        }

        @DisplayName("Given a product that is already saved Then throw error")
        @Test
        void errorSavingProductInWishList() throws  ConflictWishListException {
            when(wishProductService.addWishProducts(any()))
                    .thenThrow(new ConflictWishListException("The wish product has already saved"));


            assertThatThrownBy(()->
                ValidationsWishList.saveWishList(wishListDto,wishProductService))
            .isInstanceOf(ConflictWishListException.class);
        }
    }

}