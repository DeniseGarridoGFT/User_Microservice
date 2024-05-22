package com.workshop.users.services.wishproduct;

import com.workshop.users.exceptions.ConflictWishListException;
import com.workshop.users.exceptions.WishProductNotFoundException;
import com.workshop.users.model.WishProductEntity;
import com.workshop.users.repositories.WishProductDAORepository;
import org.springframework.stereotype.Service;


@Service
public class WishProductServiceImpl implements WishProductService {
    private final WishProductDAORepository wishProductDAORepository;

    public WishProductServiceImpl(WishProductDAORepository wishProductDAORepository) {
        this.wishProductDAORepository = wishProductDAORepository;
    }


    @Override
    public Long addWishProducts(WishProductEntity wishProductEntity) throws ConflictWishListException {

        if (wishProductDAORepository.findById(wishProductEntity.getWishProductPK()).isPresent())
            throw new ConflictWishListException("The user with id " +
                    wishProductEntity.getWishProductPK().getUserId() +
                    " already have the product with id " +
                    wishProductEntity.getWishProductPK().getProductId() +
                    " in wishes");
        else
            return wishProductDAORepository.save(wishProductEntity)
                    .getWishProductPK().getProductId();

    }

    @Override
    public void deleteWishProducts(WishProductEntity wishProductEntity) throws WishProductNotFoundException {
        if (wishProductDAORepository.findById(wishProductEntity.getWishProductPK()).isPresent())
            wishProductDAORepository.delete(wishProductEntity);
        else
            throw new WishProductNotFoundException("The product with id "
                    + wishProductEntity.getWishProductPK().getProductId()
                    + " is not in your wishes");
    }
}
