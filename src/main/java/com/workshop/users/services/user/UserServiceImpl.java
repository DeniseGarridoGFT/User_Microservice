package com.workshop.users.services.user;

import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.exceptions.*;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.CountryDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;
    CountryDAORepository countryDAORepository;
    private static Login loginDto;

    public UserServiceImpl(UserDAORepository userDAORepository, CountryDAORepository countryDAORepository){
        this.userDAORepository=userDAORepository;
        this.countryDAORepository = countryDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) throws AuthenticateException, RegisterException {
        if (user.getFidelityPoints() == null) {
            user.setFidelityPoints(0);
        }

        String encryptedPassword = loginDto.BCRYPT.encode(user.getPassword());
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
        return UserEntity.fromEntity(userDAORepository.findByEmail(email).orElseThrow(()-> new AuthenticateException("Can't authenticate")));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, InternalServerException {
        try {
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
        } catch (RuntimeException e) {
            throw new UserNotFoundException("The user with id " + id + " was not found.");
        }catch (Exception e) {
            throw new InternalServerException("Can not update the user.");
        }
    }
}
