package com.workshop.users.model.data;

import com.workshop.users.api.dto.WishListDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DataWishListDto {

    public static final WishListDto WISH_LIST_USER_1_PRODUCT_1 = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(1L))).build();
    public static final WishListDto WISH_LIST_USER_1_PRODUCT_2 =WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(3L))).build();

    public static final WishListDto WISH_LIST_USER_1_PRODUCT_3 = WishListDto.builder().userId(1L).productsIds(new HashSet<>(List.of(3L))).build();
}
