package com.workshop.users.services.wishproduct;

import com.workshop.users.api.dto.WishListDto;
import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.model.WishProductPK;
import com.workshop.users.repositories.WishProductDAORepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
public class WishProductServiceImpl implements WishProductService {
    private WishProductDAORepository wishProductDAORepository;

    public WishProductServiceImpl(WishProductDAORepository wishProductDAORepository){
        this.wishProductDAORepository = wishProductDAORepository;
    }

    @Override
    public Long addWishProducts(WishProductEntity wishProductEntity) throws ConflictWishListException {
        try {
            return wishProductDAORepository.save(wishProductEntity)
                    .getWishProductPK().getProductId();
        }catch (RuntimeException runtimeException){
            throw new ConflictWishListException("The user with this id");
        }
    }
}
