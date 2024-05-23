package com.workshop.users.api.controller;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.NotFoundProductException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.exceptions.NotFoundWishProductException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.services.product.ProductService;
import com.workshop.users.services.user.UserService;
import com.workshop.users.services.wishproduct.WishProductService;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

public class WishProductControllerTest {
    ProductService productService;
    private WishProductService wishProductService;
    UserService userService;
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
        void postWishList() throws NotFoundProductException, ConflictWishListException, NotFoundUserException {
            //Given
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.saveWishList(any(WishListDto.class), any(WishProductService.class)))
                    .thenCallRealMethod();
            //When
            ResponseEntity<WishListDto> responseEntity = wishProductController.postWishList(wishListDto);
            //Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(responseEntity.getBody()).isEqualTo(wishListDto);
        }

        @Test
        @DisplayName("Given a list with non associated user then return not found error")
        void postWishListNotFoundUser(){
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenThrow(new NotFoundUserException("Not found user"));
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.saveWishList(any(WishListDto.class), any(WishProductService.class)))
                    .thenCallRealMethod();
            assertThatThrownBy(()->
                wishProductController.postWishList(wishListDto))
                    .isInstanceOf(NotFoundUserException.class);
        }

        @Test
        @DisplayName("Given a list with non associated product then throw not found error")
        void postWishListNotFoundProduct(){
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenThrow(new NotFoundProductException("Not found product"));
            validationsWishListMock.when(() -> ValidationsWishList.saveWishList(any(WishListDto.class), any(WishProductService.class)))
                    .thenCallRealMethod();
            assertThatThrownBy(()->
                wishProductController.postWishList(wishListDto))
            .isInstanceOf(NotFoundProductException.class);
        }

        @Test
        @DisplayName("Given a list with products already saved then throw conflict error")
        void postWishListProductSavedYet(){
            validationsWishListMock.when(() -> ValidationsWishList.validateExistsProduct(any(WishListDto.class), any(ProductService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.validateUserId(any(WishListDto.class), any(UserService.class)))
                    .thenCallRealMethod();
            validationsWishListMock.when(() -> ValidationsWishList.saveWishList(any(WishListDto.class), any(WishProductService.class)))
                    .thenThrow(new ConflictWishListException("The wish has saved yet"));
           assertThatThrownBy(()->
               wishProductController.postWishList(wishListDto))
           .isInstanceOf(ConflictWishListException.class);
        }
    }

    @Nested
    @DisplayName("When delete a Wish list")
    class DeleteWishList {
        @Test
        @DisplayName("Given a good list to delete Then return a response with no content")
        void postWishList() throws NotFoundWishProductException {
            //Given
            doNothing().when(wishProductService).deleteWishProducts(any(WishProductEntity.class));
            //When
            ResponseEntity<WishListDto> responseEntity = wishProductController.deleteWishList(1L,2L);
            //Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            verify(wishProductService,times(1)).deleteWishProducts(any(WishProductEntity.class));
        }

        @Test
        @DisplayName("Given a list with non associated wish product with this id then throw not found error")
        void postWishListNotFoundUser() throws NotFoundWishProductException {
            //Given
            doThrow(new NotFoundWishProductException("Not found product")).when(wishProductService).deleteWishProducts(any(WishProductEntity.class));

            //When Then
            assertThatThrownBy(()->wishProductController.deleteWishList(1L,2L)).isInstanceOf(NotFoundWishProductException.class);

            verify(wishProductService,times(1)).deleteWishProducts(any(WishProductEntity.class));
        }
    }
}
