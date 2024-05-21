package com.workshop.users.services.wishproduct;

import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.NotFoundWishProductException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.repositories.WishProductDAORepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class WishProductServiceImpl implements WishProductService {
    private final WishProductDAORepository wishProductDAORepository;

    public WishProductServiceImpl(WishProductDAORepository wishProductDAORepository){
        this.wishProductDAORepository = wishProductDAORepository;
    }

    @Override
    public Long addWishProducts(WishProductEntity wishProductEntity) throws ConflictWishListException {
        try {
            return wishProductDAORepository.save(wishProductEntity)
                    .getWishProductPK().getProductId();
        }catch (EntityExistsException exception){
            throw new ConflictWishListException("The user with id "+
                                    wishProductEntity.getWishProductPK().getUserId()+
                                    " already have the product with id "+
                                    wishProductEntity.getWishProductPK().getProductId()+
                                    " in wishes");
        }
    }

    @Override
    public void deleteWishProducts(WishProductEntity wishProductEntity) throws NotFoundWishProductException {
        try{
            wishProductDAORepository.delete(wishProductEntity);
        }catch (EntityNotFoundException exception){
            throw new NotFoundWishProductException("The product with id "
                    +wishProductEntity.getWishProductPK().getProductId()
                    +" is not in your wishes");
        }
    }
}
