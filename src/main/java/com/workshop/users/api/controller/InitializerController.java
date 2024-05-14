package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

@RestController
public class InitializerController {
    private final UserService userService;
    private final AddressService addressService;
    private Validations validations = new Validations();

    public InitializerController(UserService userService, AddressService addressService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) throws ParseException {

        if (!validations.checkAllMethods(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AddressDto addressDto = user.getAddress();
        if (addressDto != null) {
            AddressDto createdAddress = addressService.addAddress(addressDto);
            if (createdAddress == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            user.setAddress(createdAddress);
        }

        UserDto createdUser = userService.addUser(user);
        if (createdUser == null && addressDto != null) {
            addressService.deleteAddress(addressDto);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> loginUser(@Validated @RequestBody Login userToLogIn) throws ResponseStatusException {

        try {
            UserDto userToRespones = userService.getUserByEmail(userToLogIn.getEmail());
            if (userToLogIn.passwordMatch(userToRespones.getPassword())) {
                return new ResponseEntity<>(userToRespones, HttpStatus.OK);
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect");
        }catch (RuntimeException runtimeException){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect");
        }
    }

}