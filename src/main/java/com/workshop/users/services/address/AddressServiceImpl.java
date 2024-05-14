package com.workshop.users.services.address;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.AddressEntity;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.AddressDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import com.workshop.users.services.user.UserService;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressDAORepository addressDAORepository;

    public AddressServiceImpl(AddressDAORepository addressDAORepository){

        this.addressDAORepository = addressDAORepository;
    }

    @Override
    public AddressDto addAddress(AddressDto address) throws ParseException {
        return AddressEntity.fromEntity(addressDAORepository.save(AddressDto.toEntity(address)));
    }

    @Override
    public AddressDto deleteAddress(AddressDto address) throws ParseException {
        AddressEntity addressEntity = AddressDto.toEntity(address);
        addressDAORepository.delete(addressEntity);
        return AddressEntity.fromEntity(addressEntity);
    }
}
