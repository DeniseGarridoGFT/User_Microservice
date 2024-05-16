package com.workshop.users.api.controller;

import com.workshop.users.api.dto.Product;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationsWishListTest {

    private WishListDto wishListDto;
    private WishProductService wishProductService;
    private ProductService productService;
    private UserService userService;


    @BeforeEach
    void setUp() {
        wishProductService = mock(WishProductService.class);
        productService =mock(ProductService.class);
        userService = mock(UserService.class);
        wishListDto = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(1L,2L,3L))).build();
    }

    @Nested
    @DisplayName("When try to validateUserId")
    class ValidateUserId{
        @DisplayName("Given valid userId Then don't have an error")
        @Test
        void validUserId(){
            //When
            when(userService.getUserById(1L)).thenReturn(UserDto.builder().build());
            //Then
            ValidationsWishList.validateUserId(wishListDto,userService);
        }
        @DisplayName("Given invalid userId Then throw an error")
        @Test
        void nonUserId(){
            //When
            when(userService.getUserById(1L)).thenThrow( new RuntimeException());
            //Then
            try {
                ValidationsWishList.validateUserId(wishListDto, userService);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException).hasMessage("404 NOT_FOUND \"User not found\"");
            }
        }
    }


    @Nested
    @DisplayName("When try to validate if the product that the user want to save in the wish list")
    class ValidateTheProductExists{
        @DisplayName("Given an existed product Then don't have an error")
        @Test
        void validateIfExitsProduct(){
            //When
            when(productService.findProductById(anyLong())).thenReturn(any(Product.class));
            //Then
            ValidationsWishList.validateExistsProduct(wishListDto,productService);
        }

        @DisplayName("Given a non existed product Then throw error")
        @Test
        void nonUserId(){
            //When
            when(productService.findProductById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists"));
            //Then
            try {
                ValidationsWishList.validateExistsProduct(wishListDto, productService);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException).hasMessage("404 NOT_FOUND \"The product not exists\"");
            }
        }
    }

}