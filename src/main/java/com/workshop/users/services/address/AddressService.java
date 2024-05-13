package com.workshop.users.services.address;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface AddressService {
    AddressDto addAddress(AddressDto address) throws ParseException;

}
