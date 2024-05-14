package com.workshop.users.api.controller;
import com.workshop.users.api.dto.UserDto;
import org.springframework.http.HttpStatus;
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
    private UserService userService = new UserService() {
        @Override
        public UserDto addUser(UserDto user) throws ParseException {
            return null;
        }

        @Override
        public UserDto getUserById(Long id) throws RuntimeException {
            return null;
        }

        @Override
        public UserDto getUserByEmail(String email) throws RuntimeException {
            return null;
        }

        @Override
        public UserDto updateUser(Long id, UserDto userDto) {
            return null;
        }
    };
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

    boolean checkSameEmail(UserDto userToCheck) {
        String email = userToCheck.getEmail();
        if (email != null) {
            try {
                userService.getUserByEmail(email);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your email is already registered");
            } catch (RuntimeException e) {
            }
        }
        return true;
    }

    boolean checkAllMethods(UserDto userToCheck) throws ResponseStatusException{
        return checkEmail(userToCheck) && checkPassword(userToCheck) && checkDateFormat(userToCheck) && checkPhone(userToCheck) && checkAge(userToCheck) && checkSameEmail(userToCheck);
    }


}
