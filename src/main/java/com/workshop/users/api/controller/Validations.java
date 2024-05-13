package com.workshop.users.api.controller;

import com.workshop.users.api.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class Validations {
    boolean checkEmail(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkFormatEmail()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid email format.");
        }
        return true;
    }

    boolean checkPassword(UserDto userToCheck) throws ResponseStatusException {
        if (!userToCheck.checkSecurityPassword()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The password must contain, at least, 8 alphanumeric characters.");
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

    boolean checkAllMethods(UserDto userToCheck) throws ResponseStatusException{
        return checkEmail(userToCheck) && checkPassword(userToCheck) && checkDateFormat(userToCheck) && checkPhone(userToCheck) && checkAge(userToCheck);
    }

}
