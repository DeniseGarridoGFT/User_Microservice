package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;

@RestController
public class InitializerController {
    private final UserService userService;
    private final AddressService addressService;
    private Validations validations;

    public InitializerController(UserService userService, AddressService addressService,Validations validations) {
        this.addressService = addressService;
        this.userService = userService;
        this.validations =validations;
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
            UserDto createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }


    @PostMapping("/login")
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