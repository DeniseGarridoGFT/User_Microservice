package com.workshop.users.services.user;

import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.*;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.CountryDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAORepository userDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) throws AuthenticateException, RegisterException {

        String encryptedPassword = Login.BCRYPT.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setFidelityPoints(0);

        if (user.getId() != null && userDAORepository.findById(user.getId()).isPresent()) {
            throw new RegisterException("There's an error registering the user");
        }
        return UserEntity.fromEntity(userDAORepository.save(UserDto.toEntity(user)));

    }


    @Override
    public UserDto getUserById(Long id) throws NotFoundUserException {
        return UserEntity.fromEntity(userDAORepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("Not found user")));

    }

    @Override
    public UserDto getUserByEmail(String email) throws AuthenticateException {
        return UserEntity.fromEntity(userDAORepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticateException("Can't authenticate")));
    }

    @Override
    public Optional<UserEntity> getUserByEmailOptional(String email) {
        return userDAORepository.findByEmail(email);
    }

    @Override
    public UserDto updateFidelityPoints(Long id, Integer points) throws NotFoundUserException {
        UserEntity userToUpdate = userDAORepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("Not found user"));

        userToUpdate.setFidelityPoints(UserDto.setSaveFidelityPoints(userToUpdate.getFidelityPoints(), points));

        return UserEntity.fromEntity(userDAORepository.save(userToUpdate));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws NotFoundUserException{
        UserEntity userEntity = userDAORepository.findById(id).orElseThrow(()->new NotFoundUserException("The user with this id not exists"));
        userEntity.setName(userDto.getName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setBirthDate(new Date(userDto.getBirthDate()));
        userEntity.setPassword(Login.BCRYPT.encode(userDto.getPassword()));
        userEntity.setPhone(userDto.getPhone());
        userEntity.setAddress(AddressDto.toEntity(userDto.getAddress()));
        userEntity.setCountry(CountryDto.toEntity(userDto.getCountry()));
        return UserEntity.fromEntity(userDAORepository.save(userEntity));

    }



}

