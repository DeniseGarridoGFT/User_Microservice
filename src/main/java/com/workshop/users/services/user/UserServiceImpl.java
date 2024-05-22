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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;
    private static CountryDAORepository countryDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository, CountryDAORepository countryDAORepository){
        this.userDAORepository=userDAORepository;
        this.countryDAORepository = countryDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) throws AuthenticateException, RegisterException {
        if (user.getFidelityPoints() == null) {
            user.setFidelityPoints(0);
        }

        String encryptedPassword = Login.BCRYPT.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        if (user.getId()!=null&&userDAORepository.findById(user.getId()).isPresent()){
            throw new RegisterException("There's an error registering the user");
        }
        return UserEntity.fromEntity(userDAORepository.save(UserDto.toEntity(user)));

    }


    @Override
    public UserDto getUserById(Long id) throws  NotFoundUserException {
        return UserEntity.fromEntity(userDAORepository.findById(id)
                .orElseThrow(()->new NotFoundUserException("Not found user")));

    }

    @Override
    public UserDto getUserByEmail(String email) throws AuthenticateException {
        return UserEntity.fromEntity(userDAORepository.findByEmail(email)
                .orElseThrow(()-> new AuthenticateException("Can't authenticate")));
    }
    @Override
    public Optional<UserEntity> getUserByEmailOptional(String email) {
        return userDAORepository.findByEmail(email);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, InternalServerException {
            UserEntity userEntity = userDAORepository.findById(id).orElseThrow();
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
