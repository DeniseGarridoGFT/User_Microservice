package com.workshop.users.api.controller;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.CustomException;


public class Validations {
    boolean checkEmail(UserDto userToCheck) throws CustomException {
        if (!userToCheck.checkFormatEmail()) {
            throw new CustomException("Invalid email format.");
        }
        return userToCheck.checkFormatEmail();
    }
}
