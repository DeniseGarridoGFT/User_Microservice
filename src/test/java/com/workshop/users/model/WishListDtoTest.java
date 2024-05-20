package com.workshop.users.model;

import com.workshop.users.api.dto.WishListDto;

import static org.assertj.core.api.Assertions.*;

import com.workshop.users.model.data.DataWishListDto;
import com.workshop.users.model.data.DataWishProductPK;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class WishListDtoTest {
    @Nested
    @DisplayName("Given a good user_id When comprobe is empty product")
    class isEmptyGoodUserTest {
        @Test
        @DisplayName("Given a not empty Set Then return false")
        void isEmptyTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            assertThat(wishListDto.isEmpty()).isEqualTo(false);
        }

        @Test
        @DisplayName("Given a empty Set Then throws error 400")
        void isEmptyFalseTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(new HashSet<>()).build();
            try{
                wishListDto.isEmpty();
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class)
                        .hasMessage("400 BAD_REQUEST \"You have to send one productId\"");
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }

        @Test
        @DisplayName("Given a null Set Then throws error 400")
        void isEmptyNullTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(null).build();
            try{
                wishListDto.isEmpty();
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class)
                        .hasMessage("400 BAD_REQUEST \"You have to send one productId\"");
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Test
    @DisplayName("Given a userid and product id " +
            "When getEntity WishListDto" +
            "Then return WishPoductEntity")
    void getEntity(){
        WishProductEntity wishProductEntity = new WishProductEntity();
        WishProductPK wishProductPK = new WishProductPK();
        wishProductPK.setUserId(1L);
        wishProductPK.setProductId(2L);
        wishProductEntity.setWishProductPK(wishProductPK);
        assertThat(WishListDto.getEntity(1L,2L))
                .isInstanceOf(WishProductEntity.class)
                .isEqualTo(wishProductEntity);
    }



    @Nested
    @DisplayName("Given a not empty set When comprobe is empty user")
    class isEmptyGoodSettTest {
        @Test
        @DisplayName("Given a userId Then return false")
        void isEmptyTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            assertThat(wishListDto.isEmpty()).isEqualTo(false);
        }

        @Test
        @DisplayName("Given a null userId Set Then throws error 400")
        void isEmptyNull() {
            WishListDto wishListDto = WishListDto.builder().userId(null).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            try{
                wishListDto.isEmpty();
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class)
                        .hasMessage("400 BAD_REQUEST \"You have to send one userId\"");
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }



    @Nested
    @DisplayName("When comprobe is null product")
    class isNullProductsGoodSettTest {
        @Test
        @DisplayName("Given a set of productsIds Then return false")
        void isNullProductsTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            assertThat(wishListDto.isNullProducts()).isEqualTo(false);
        }

        @Test
        @DisplayName("Given a null Set Then throws error 400")
        void isNullProductsErrorTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(null).build();
            try{
                wishListDto.isNullProducts();
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class)
                        .hasMessage("400 BAD_REQUEST \"You have to send one productId\"");
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }


    }

    @Nested
    @DisplayName("When comprobe is null userId")
    class isNullUserIdGoodSettTest {
        @Test
        @DisplayName("Given a set of productsIds Then return false")
        void isNullUserIdTest() {
            WishListDto wishListDto = WishListDto.builder().userId(2L).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            assertThat(wishListDto.isNullUserId()).isEqualTo(false);
        }

        @Test
        @DisplayName("Given a null  Set Then throws error 400")
        void isNullUserIdGivenNullTest() {
            WishListDto wishListDto = WishListDto.builder().userId(null).productsIds(new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L))).build();
            try{
                wishListDto.isNullUserId();
            }catch (ResponseStatusException exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class)
                        .hasMessage("400 BAD_REQUEST \"You have to send one userId\"");
                assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }


}
