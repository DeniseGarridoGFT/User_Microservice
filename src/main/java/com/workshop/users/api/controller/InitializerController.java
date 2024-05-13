package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.PasswordDoentMatchException;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;
import org.h2.engine.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class InitializerController {
    private final UserService userService;
    private final AddressService addressService;


    public InitializerController(UserService userService,AddressService addressService) {
        this.addressService=addressService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@Validated @RequestBody UserDto user) throws ParseException {
        AddressDto addressDto = user.getAddress();
        user.setAddress(addressService.addAddress(addressDto));
        UserDto createdUser = userService.addUser(user);
        if(!user.checkFormatEmail()){
            return new ResponseEntity<>(createdUser, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody Login userToLogIn){
        UserDto userToRespones = userService.getUserByEmail(userToLogIn.getEmail());
        if (userToLogIn.passwordMatch(userToRespones.getPassword())){
            return new ResponseEntity<>(userToRespones,HttpStatus.OK);
        }
        return new ResponseEntity<>(new PasswordDoentMatchException("The password or the email are incorrect"),PasswordDoentMatchException.STATUS_CODE);
    }

}