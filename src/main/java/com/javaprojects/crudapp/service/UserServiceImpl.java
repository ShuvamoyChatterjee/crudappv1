package com.javaprojects.crudapp.service;

import com.javaprojects.crudapp.dto.UserDto;
import com.javaprojects.crudapp.exception.InvalidUserException;
import com.javaprojects.crudapp.model.User;
import com.javaprojects.crudapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public User createUser(UserDto userDto) {
        User userEntity = null;
        if(Objects.nonNull(userDto)){
            userEntity = modelMapper.map(userDto,User.class);
        }
        User persistedUser=null;
        if(Objects.nonNull(userEntity)){
            persistedUser = userRepository.save(userEntity);
        }
        return persistedUser;
    }

    @Override
    public UserDto getUserById(String userId) {
        var optionalUser = userRepository.findById(UUID.fromString(userId));

        if(optionalUser.isPresent()){
            return modelMapper.map(optionalUser.get(), UserDto.class);
        }
        else{
            throw new InvalidUserException();
        }
    }

    @Override
    public User updateUser(UserDto user, String userId) {
        Optional<User> optionalUser = Optional.empty();
        if(Objects.nonNull(userId)){
            optionalUser = userRepository.findById(UUID.fromString(userId));
            if(optionalUser.isPresent()){
                var userEntity = optionalUser.get();
                userEntity.setName(user.getName());
                userEntity.setPhoneNumber(user.getPhoneNumber());
                userEntity.setEmail(user.getEmail());

                userEntity = userRepository.save(userEntity);
                return userEntity;
            }
        }

        if(optionalUser.isEmpty()){
            log.error("Exception thrown in service class");
            throw new InvalidUserException();
        }
        return null;
    }

    @Override
    public void deleteUserById(String userId) {
        try {
            if(Objects.nonNull(userId)){
                userRepository.deleteById(UUID.fromString(userId));
            }
            else{
                throw new IllegalArgumentException();
            }
        }
        catch (Exception ex){
            log.error("Exception in service :"+ex.getMessage());
            throw new InvalidUserException();
        }
    }
}
