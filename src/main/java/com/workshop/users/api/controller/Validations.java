package com.workshop.users.api.controller;
import com.workshop.users.api.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.UserDAORepository;
import com.workshop.users.services.user.UserService;
import org.h2.engine.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Optional;


public class Validations {
    private UserService userService;

    public Validations(UserService userService){
        this.userService=userService;
    }

    boolean checkEmail(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkFormatEmail()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid email format.");
        }
        return true;
    }
    boolean checkPassword(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkSecurityPassword()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The password must contain, at least, 8 alphanumeric characters, uppercase, lowercase an special character");
        }
        return true;
    }
    boolean checkDateFormat(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkBirthDateFormat()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The format of the birthd date is not valid");
        }
        return true;
    }
    boolean checkPhone(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkPhoneFormat()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The phone must contain 9 numeric characters.");
        }
        return true;
    }
    boolean checkAge(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkOver18()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user must be of legal age.");
        }
        return true;
    }

    boolean checkSameEmail(UserDto userToCheck) throws ResponseStatusException{
        String email = userToCheck.getEmail();
            try {
                userService.getUserByEmail(email);
            } catch (RuntimeException e) {
                return true;
            }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your email is already registered");
    }


    boolean checkAllMethods(UserDto userToCheck) throws ResponseStatusException{
        return checkEmail(userToCheck) && checkPassword(userToCheck) && checkDateFormat(userToCheck) && checkPhone(userToCheck) && checkAge(userToCheck) && checkSameEmail(userToCheck);
    }


}
