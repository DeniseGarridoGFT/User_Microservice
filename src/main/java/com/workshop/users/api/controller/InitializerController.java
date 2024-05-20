package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.AuthenticateException;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.country.CountryService;
import com.workshop.users.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController
public class InitializerController {
    private final UserService userService;
    private final AddressService addressService;
    private CountryService countryService;
    private Validations validations;

    public InitializerController(UserService userService, AddressService addressService,Validations validations, CountryService countryService) {
        this.addressService = addressService;
        this.userService = userService;
        this.validations =validations;
        this.countryService = countryService;
    }

    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) throws ResponseStatusException {
        validations.checkAllMethods(user);
        try {

            AddressDto addressDto = user.getAddress();
            if (addressDto != null) {
                AddressDto createdAddress = addressService.addAddress(addressDto);
                user.setAddress(createdAddress);
            }

            CountryDto countryDto = user.getCountry();
            if (countryDto != null) {
                CountryDto country = countryService.getCountryByName(countryDto.getName());
                user.setCountry(country);
            }

            UserDto createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@Validated @RequestBody Login userToLogIn) throws AuthenticateException {
        UserDto userToRespones = userService.getUserByEmail(userToLogIn.getEmail());
        if (userToLogIn.passwordMatch(userToRespones.getPassword())) {
            return new ResponseEntity<>(userToRespones, HttpStatus.OK);
        }
        throw new AuthenticateException("Can't authenticate");
    }
}