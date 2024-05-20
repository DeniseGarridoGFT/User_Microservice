package com.workshop.users.services.user;

import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.CountryEntity;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.CountryDAORepository;
import com.workshop.users.repositories.UserDAORepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;
    private CountryDAORepository countryDAORepository;

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
        CountryEntity countryEntity = countryDAORepository.findByName(user.getCountry().getName())
                .orElseThrow(() -> new IllegalArgumentException("Sorry! We're not in that country yet. We deliver to España, Estonia, Finalndia, Francia, Italia, Portugal, Grecia"));
        String encryptedPassword = loginDto.BCRYPT.encode(user.getPassword());
        user.setCountry(CountryEntity.fromEntity(countryEntity));
        user.setPassword(encryptedPassword);

        return UserEntity.fromEntity(userDAORepository.save(UserDto.toEntity(user)));
    }


    @Override
    public UserDto getUserById(Long id) throws RuntimeException {
        isNotNull(id);
        return UserEntity.fromEntity(userDAORepository.findById(id).orElseThrow());
    }

    @Override
    public UserDto getUserByEmail(String email) throws RuntimeException{
        isNotNull(email);
        return UserEntity.fromEntity(userDAORepository.findByEmail(email).orElseThrow());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = userDAORepository.findById(id).orElseThrow();
        userEntity.setName(userDto.getName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setBirthDate(new Date(userDto.getBirthDate()));
        userEntity.setPassword(userDto.getPassword());
        userEntity.setFidelityPoints(userDto.getFidelityPoints());
        userEntity.setPhone(userDto.getPhone());
        userEntity.setAddress(AddressDto.toEntity(userDto.getAddress()));

        return UserEntity.fromEntity(userDAORepository.save(userEntity));
    }


    public void isNotNull(Object id) throws RuntimeException{
        if (id==null){
            throw new RuntimeException("Request not valid");
        }
    }
}
