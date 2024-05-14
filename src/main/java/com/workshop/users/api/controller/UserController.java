package com.workshop.users.api.controller;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.AddressEntity;
import com.workshop.users.repositories.AddressDAORepository;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.address.AddressServiceImpl;
import com.workshop.users.services.user.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class UserController {

    private final UserService userService;
    private Validations validations = new Validations();
    private AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {

        this.userService = userService;
        this.addressService = addressService;
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
        if (updatedUserDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        validations.checkAllMethods(updatedUserDto);
        AddressDto updatedUserAddress = addressService.updateAddress(updatedUserDto.getAddress().getId(), updatedUserDto.getAddress());
        updatedUserDto.setAddress(updatedUserAddress);
        UserDto updatedUser = userService.updateUser(id,updatedUserDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}