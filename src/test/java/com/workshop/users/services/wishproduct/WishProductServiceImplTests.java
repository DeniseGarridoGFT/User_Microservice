package com.workshop.users.services.wishproduct;

import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.model.WishProductPK;
import com.workshop.users.repositories.WishProductDAORepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class WishProductServiceImplTests {
    private WishProductDAORepository wishProductDAORepository;
    private WishProductService wishProductService;
    private WishProductEntity wishProductEntity;
    private WishProductPK wishProductPK;

    @BeforeEach
    void setUp() {
        wishProductDAORepository = Mockito.mock(WishProductDAORepository.class);
        wishProductService = new WishProductServiceImpl(wishProductDAORepository);
        wishProductEntity = new WishProductEntity();
        wishProductPK = new WishProductPK();
        wishProductPK.setProductId(1L);
        wishProductPK.setUserId(1L);
        wishProductEntity.setWishProductPK(wishProductPK);
    }



    @Nested
    @DisplayName("When exists WishProduct")
    class AddWishProduct{
        @Test
        @DisplayName("Given wishList to add " +
                "Then return the same wishList")
        void addWishList() throws ConflictWishListException {
            //Given
            when(wishProductDAORepository.save(wishProductEntity))
                    .thenReturn(wishProductEntity);
            //When and Then
            assertThat(wishProductService.addWishProducts(wishProductEntity))
                    .isEqualTo(1L);
        }
        @Test
        @DisplayName("Given wish list with a product that already is wished " +
                "Then throw Exception")
        void addWishListThrowError() {
            //Given
            when(wishProductDAORepository.save(wishProductEntity))
                    .thenThrow(new RuntimeException());
            //When and Then
            assertThatThrownBy(() -> {
                wishProductService.addWishProducts(wishProductEntity);
            }).isInstanceOf(ConflictWishListException.class);
        }
    }



}
