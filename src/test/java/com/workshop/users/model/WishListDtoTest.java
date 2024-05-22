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


}
