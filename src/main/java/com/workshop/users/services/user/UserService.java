package com.workshop.users.services.user;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.InternalServerException;
import com.workshop.users.exceptions.NotFoundUserException;
import com.workshop.users.model.UserEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public interface UserService {
    UserDto addUser(UserDto user) throws ParseException;
    UserDto getUserById(Long id) throws RuntimeException, NotFoundUserException;
    UserDto getUserByEmail(String email) throws RuntimeException;
    UserDto updateUser(Long id, UserDto userDto) throws Exception, InternalServerException;
    Optional<UserEntity> getUserByEmailOptional(String email);
}
