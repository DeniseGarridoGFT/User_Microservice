package com.workshop.users.services.user;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.UserDAORepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserDAORepository userDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository){
        this.userDAORepository=userDAORepository;
    }


    @Override
    public UserDto addUser(UserDto user) {
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
        return null;
    }


    public void isNotNull(Object id) throws RuntimeException{
        if (id==null){
            throw new RuntimeException("Request not valid");
        }
    }
}
