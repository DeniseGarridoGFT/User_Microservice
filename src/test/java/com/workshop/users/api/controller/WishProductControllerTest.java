package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

public class WishProductControllerTest {
    private ProductService productService;
    private WishProductService wishProductService;
    private UserService userService;
    private WishProductController wishProductController;
    private WishListDto wishListDto;
    private  MockedStatic<ValidationsWishList> validationsWishListMock;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        wishProductService = mock(WishProductService.class);
        userService = mock(UserService.class);
        wishProductController = new WishProductController(wishProductService,userService,productService);
        wishListDto = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(1L,2L,3L,4L))).build();
        validationsWishListMock = mockStatic(ValidationsWishList.class);

    }

    @AfterEach
    void tearDown() {
        // Deshacer la mock estática de ValidationsWishList después de cada prueba
        validationsWishListMock.close();
    }

    @Nested
    @DisplayName("When post a Wish list")
    class PostWishList{
        @Test
        @DisplayName("Given a good list Then throw the wish list created")
        void postWishList() {
            //Given
            when(wishProductService.addWishProducts(wishListDto)).thenReturn(wishListDto);
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            //When
            ResponseEntity<WishListDto> responseEntity = wishProductController.postWishList(wishListDto);
            //Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isEqualTo(wishListDto);
        }

        @Test
        @DisplayName("Given a list with product is already saved by the user then thow constrait error")
        void postWishListConstraitError(){
            when(wishProductService.addWishProducts(wishListDto)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT,"The product with id 1 is already exists"));
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            try {
                wishProductController.postWishList(wishListDto);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                assertThat(responseStatusException).hasMessage("409 CONFLICT \"The product with id 1 is already exists\"");
            }
        }
        @Test
        @DisplayName("Given a list with non associated user then thow not found error")
        void postWishListNotFoundUser(){
            when(wishProductService.addWishProducts(wishListDto)).thenReturn(wishListDto);
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            try {
                wishProductController.postWishList(wishListDto);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException).hasMessage("404 NOT_FOUND \"User not found\"");
            }
        }

        @Test
        @DisplayName("Given a list with non associated product then thow not found error")
        void postWishListNotFoundProduct(){
            when(wishProductService.addWishProducts(wishListDto)).thenReturn(wishListDto);
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"The product not exists"));
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            try {
                wishProductController.postWishList(wishListDto);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseStatusException).hasMessage("404 NOT_FOUND \"The product not exists\"");
            }
        }
    }
}
