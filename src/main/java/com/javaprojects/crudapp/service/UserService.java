package com.javaprojects.crudapp.service;

import com.javaprojects.crudapp.dto.UserDto;
import com.javaprojects.crudapp.model.User;

import java.util.UUID;

public interface UserService {

    User createUser(UserDto userDto);

    UserDto getUserById(String userId);

    User updateUser(UserDto user,String userId);

    void deleteUserById(String userId);
}
