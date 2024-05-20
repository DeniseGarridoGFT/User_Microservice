package com.workshop.users.services.wishproduct;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.model.WishProductPK;

public interface WishProductService {


    Long addWishProducts(WishProductEntity wishProductEntity) throws ConflictWishListException;
}
