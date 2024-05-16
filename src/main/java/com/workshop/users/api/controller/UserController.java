package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.user.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

@RestController
public class UserController {
    private UserService userService = null;
    private Validations validations;
    private AddressService addressService;



    public UserController(UserService userService, AddressService addressService, Validations validations) {

        this.userService = userService;
        this.addressService = addressService;
        this.validations = validations;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@Validated @PathVariable("id") Long id) {
        try {
            UserDto user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto updatedUserDto) throws ParseException {
        validations.checkAllMethods(updatedUserDto);
        try{
            if (updatedUserDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            AddressDto updatedUserAddress = addressService.updateAddress(updatedUserDto.getAddress().getId(), updatedUserDto.getAddress());
            updatedUserDto.setAddress(updatedUserAddress);
            UserDto updatedUser = userService.updateUser(id,updatedUserDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }

    }
}