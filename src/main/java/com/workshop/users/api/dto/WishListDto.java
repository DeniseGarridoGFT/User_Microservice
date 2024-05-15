package com.workshop.users.api.dto;

import com.workshop.users.model.WishProductPK;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Data
@Builder
public class WishListDto {
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


    public List<WishProductPK> toEntity() {
        isEmpty();
        return getProductsIds().stream().reduce(new LinkedList<>(), (listEntities, productId) -> {
            WishProductPK wishProductPK = new WishProductPK();
            wishProductPK.setUserId(getUserId());
            wishProductPK.setProductId(productId);
            listEntities.add(wishProductPK);
            return listEntities;
        }, (list1, list2) -> list1);
    }

    public List<WishListDto> toDividedWisListDtos(){
        return getProductsIds()
                .stream()
                .reduce(new LinkedList<>(),(wishListDtos, idProduct) ->{
                    wishListDtos.add( WishListDto.builder()
                            .userId(userId)
                            .productsIds(new HashSet<>(Collections.singletonList(idProduct)))
                            .build());
                    return wishListDtos;
                },(list1,lis2)->list1 );
    }


}
