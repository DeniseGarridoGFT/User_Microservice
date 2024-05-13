package com.workshop.users.api.controller;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,
                                               @RequestBody UserDto updatedUserDto) throws ParseException {
        UserDto existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setName(updatedUserDto.getName());
        existingUser.setLastName(updatedUserDto.getLastName());
        existingUser.setEmail(updatedUserDto.getEmail());
        existingUser.setAddress(updatedUserDto.getAddress());
        existingUser.setBirthDate(updatedUserDto.getBirthDate());
        existingUser.setPassword(updatedUserDto.getPassword());
        existingUser.setPhone(updatedUserDto.getPhone());
        existingUser.setFidelityPoints(updatedUserDto.getFidelityPoints());
        existingUser.setCountry(updatedUserDto.getCountry());

        UserDto updatedUser = userService.addUser(existingUser);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


}