package com.workshop.users.api.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Data
@Builder
public class WishListDto {
    private Long userId;
    private Set<Long> productsIds;

    public boolean isNullUserId()  throws ResponseStatusException {
        if (userId==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You have to send one userId");
        return false;
    }

    public boolean isNullProducts() throws ResponseStatusException {
        if (productsIds==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You have to send one productId");
        return false;
    }

    public boolean isEmpty() throws ResponseStatusException{
        isNullProducts();
        isNullUserId();
        if (productsIds.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You have to send one productId");
        }
        return false;
    }
}
