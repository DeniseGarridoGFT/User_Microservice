package com.workshop.users.services.user;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.NotFoundUserException;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface UserService {
    UserDto addUser(UserDto user) throws ParseException;
    UserDto getUserById(Long id) throws RuntimeException, NotFoundUserException;
    UserDto getUserByEmail(String email) throws RuntimeException;
    UserDto updateUser(Long id, UserDto userDto) throws Exception;
}
