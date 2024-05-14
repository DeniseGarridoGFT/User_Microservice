package com.workshop.users.services.user;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.UserDAORepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository){
        this.userDAORepository=userDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) {
        if (user.getFidelityPoints() == null) {
            user.setFidelityPoints(0);
        }
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
