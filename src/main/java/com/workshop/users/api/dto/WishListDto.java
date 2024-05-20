package com.workshop.users.api.dto;

import com.workshop.users.model.WishProductEntity;
import com.workshop.users.model.WishProductPK;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
public class WishListDto implements Serializable {
    private Long userId;
    private Set<Long> productsIds;

    public boolean isNullUserId() throws ResponseStatusException {
        if (userId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to send one userId");
        return false;
    }

    public boolean isNullProducts() throws ResponseStatusException {
        if (productsIds == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to send one productId");
        return false;
    }

    public boolean isEmpty() throws ResponseStatusException {
        isNullProducts();
        isNullUserId();
        if (productsIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to send one productId");
        }
        return false;
    }

    public static WishProductEntity getEntity(Long userId,Long productId){
        WishProductEntity wishProductEntity = new WishProductEntity();
        WishProductPK wishProductPK = new WishProductPK();
        wishProductPK.setUserId(userId);
        wishProductPK.setProductId(productId);
        wishProductEntity.setWishProductPK(wishProductPK);
        return wishProductEntity;
    }




}
