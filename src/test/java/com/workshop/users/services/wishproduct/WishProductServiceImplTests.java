package com.workshop.users.services.wishproduct;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.model.WishProductPK;
import com.workshop.users.repositories.WishProductDAORepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WishProductServiceImplTests {
    private WishProductDAORepository wishProductDAORepository;
    private WishProductService wishProductService;
    private WishProductEntity wishProductEntity;
    private WishProductPK wishProductPK;
    private WishListDto wishListDto;

    @BeforeEach
    void setUp() {
        wishProductDAORepository = Mockito.mock(WishProductDAORepository.class);
        wishProductService = new WishProductServiceImpl(wishProductDAORepository);
        wishProductEntity = new WishProductEntity();
        wishProductPK = new WishProductPK();
        wishListDto = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(1L))).build();
        wishProductPK.setProductId(1L);
        wishProductPK.setUserId(1L);
        wishProductEntity.setWishProductPK(wishProductPK);
    }

    @Nested
    @DisplayName("When exists WishProduct")
    class findWishProductById{
        @Test
        @DisplayName("Given existing id then return true")
        void exitsWishProduct() {
            //Given
            Mockito.when(wishProductDAORepository.findById(wishProductPK)).thenReturn(Optional.of(wishProductEntity));
            //Then
            Assertions.assertThat(wishProductService.existsWishProduct(wishListDto)).isTrue();
        }
        @Test
        @DisplayName("Given non existed id then throw Exception")
        void nonExistingWishProduct() {
            //Given
            Mockito.when(wishProductDAORepository.findById(wishProductPK)).thenReturn(Optional.empty());
            //Then
            Assertions.assertThat(wishProductService.existsWishProduct(wishListDto)).isFalse();
        }
    }

    @Nested
    @DisplayName("When exists WishProduct")
    class AddWishProduct{
        @Test
        @DisplayName("Given wishList to add then return the same wishList")
        void addWishList() {
            //Given
            Mockito.when(wishProductDAORepository.save(Mockito.any(WishProductEntity.class))).thenReturn(wishProductEntity);
            //Then
            Assertions.assertThat(wishProductService.addWishProducts(wishListDto)).isEqualTo(wishListDto);
        }
        @Test
        @DisplayName("Given wish list with a product that already is wished then throw Exception")
        void addWishListThrowError() {
            //Given
            Mockito.when(wishProductDAORepository.save(Mockito.any(WishProductEntity.class)))
                    .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT,"The product with id "
                    +wishProductEntity.getWishProductPK().getProductId()
                    +" is already exists"));
            //When and Then
            try {
                wishProductService.addWishProducts(wishListDto);
            }catch (Exception exception){
                assertThat(exception).isInstanceOf(ResponseStatusException.class);
                ResponseStatusException responseStatusException = (ResponseStatusException) exception;
                assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                assertThat(responseStatusException).hasMessage("409 CONFLICT \"The product with id "
                                                +wishProductEntity.getWishProductPK().getProductId()
                                                +" is already exists\"");
            }

        }
    }



}
