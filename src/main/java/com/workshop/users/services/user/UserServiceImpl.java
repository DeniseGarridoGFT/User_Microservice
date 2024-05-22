package com.workshop.users.services.user;

import com.workshop.users.api.controller.Validations;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.*;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.CountryDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.country.CountryService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;
    private CountryDAORepository countryDAORepository;
    private Validations validations;
    AddressService addressService;
    CountryService countryService;

    private Login loginDto;

    public UserServiceImpl(UserDAORepository userDAORepository, CountryDAORepository countryDAORepository){
        this.userDAORepository=userDAORepository;
        this.countryDAORepository = countryDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) {
        if (user.getFidelityPoints() == null) {
            user.setFidelityPoints(0);
        }

        String encryptedPassword = loginDto.BCRYPT.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return UserEntity.fromEntity(userDAORepository.save(UserDto.toEntity(user)));
    }


    @Override
    public UserDto getUserById(Long id) throws  NotFoundUserException {
        try{
            return UserEntity.fromEntity(userDAORepository.findById(id).orElseThrow());
        }catch (RuntimeException runtimeException){
            throw new NotFoundUserException("Not found user");
        }

    }

    @Override
    public UserDto getUserByEmail(String email) throws AuthenticateException {
        try {
            return UserEntity.fromEntity(userDAORepository.findByEmail(email).orElseThrow());
        }catch (RuntimeException runtimeException){
            throw new AuthenticateException("Can't authenticate");
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, InternalServerException {

            UserEntity userEntity = userDAORepository.findById(id).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " was not found"));
            userEntity.setName(userDto.getName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setBirthDate(new Date(userDto.getBirthDate()));
            userEntity.setPassword(userDto.getPassword());
            userEntity.setFidelityPoints(userDto.getFidelityPoints());
            userEntity.setPhone(userDto.getPhone());
            userEntity.setAddress(AddressDto.toEntity(userDto.getAddress()));
            userEntity.setCountry(CountryDto.toEntity(userDto.getCountry()));

            return UserEntity.fromEntity(userDAORepository.save(userEntity));



    }


    public void isNotNull(Object id) throws RuntimeException{
        if (id==null){
            throw new RuntimeException("Request not valid");
        }
    }
}
