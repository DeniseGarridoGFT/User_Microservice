package com.workshop.users.services.address;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.AddressServiceException;
import com.workshop.users.exceptions.NotFoundAddressException;
import com.workshop.users.exceptions.RegisterException;
import com.workshop.users.model.AddressEntity;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.AddressDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import com.workshop.users.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

import static jdk.dynalink.linker.support.Guards.isNotNull;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressDAORepository addressDAORepository;

    public AddressServiceImpl(AddressDAORepository addressDAORepository){
        this.addressDAORepository = addressDAORepository;
    }

    @Override
    public AddressDto addAddress(AddressDto address) throws RegisterException {
        if (address.getId()!=null && addressDAORepository.findById(address.getId()).isPresent()){
            throw new AddressServiceException("There's an error registering the address");
        }
        return AddressEntity.fromEntity(addressDAORepository.save(AddressDto.toEntity(address)));
    }

    @Override
    public AddressDto getAddressById(Long id) throws NotFoundAddressException {
        isNotNull(id);
        return AddressEntity.fromEntity(addressDAORepository.findById(id).orElseThrow());
    }



    private void isNotNull(Long id) {
        if (id == null){
            throw new NotFoundAddressException("Request not valid. The address Id is null");
        }
    }


    @Override
    public AddressDto updateAddress(Long id, AddressDto updatedAddressDto) throws RegisterException {
        AddressEntity addressEntity = addressDAORepository.findById(updatedAddressDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressEntity.setCityName(updatedAddressDto.getCityName());
        addressEntity.setZipCode(updatedAddressDto.getZipCode());
        addressEntity.setStreet(updatedAddressDto.getStreet());
        addressEntity.setNumber(updatedAddressDto.getNumber());
        addressEntity.setDoor(updatedAddressDto.getDoor());

        return AddressEntity.fromEntity(addressDAORepository.save(addressEntity));
    }
}