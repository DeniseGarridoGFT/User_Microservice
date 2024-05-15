package com.workshop.users.services.wishproduct;

import com.workshop.users.api.dto.WishListDto;
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
    public boolean existsWishProduct(WishListDto wishProductDto) {
        WishProductPK wishProductPKList = wishProductDto.toEntity().stream().findFirst().orElseThrow();
        try {
            wishProductDAORepository.findById(wishProductPKList).orElseThrow();
        }catch (RuntimeException exception){
            return false;
        }
        return true;
    }

    @Override
    public WishListDto addWishProducts(WishListDto wishListDto) {
        List<WishProductEntity> wishProductEntities = wishListDto.toEntity().stream()
                                                            .reduce(new LinkedList<WishProductEntity>(),(entitiesList,pk)->{
                                                                WishProductEntity wishProductEntity = new WishProductEntity();
                                                                wishProductEntity.setWishProductPK(pk);
                                                                entitiesList.add(wishProductEntity);
                                                                return entitiesList;
                                                            },(list1,list2)->list1);
        for (WishProductEntity wishProductEntity: wishProductEntities)
            try {
                wishProductDAORepository.save(wishProductEntity);
            }catch(Exception exc){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"The product with id "
                        +wishProductEntity.getWishProductPK().getProductId()
                        +" is already exists");
            }
        return wishListDto;
    }
}
