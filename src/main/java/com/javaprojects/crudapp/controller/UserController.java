package com.javaprojects.crudapp.controller;

import com.javaprojects.crudapp.dto.UserDto;
import com.javaprojects.crudapp.exception.InvalidUserException;
import com.javaprojects.crudapp.model.User;
import com.javaprojects.crudapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello/{userName}")
    public ResponseEntity<String> getAppHealth(@PathVariable String userName){
        log.info("Application running...");
        return new ResponseEntity<>(String.format("Hello %s App running", userName),HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        var optionalUser = Optional.ofNullable(userService.getUserById(userId));
        if(optionalUser.isPresent()){
            return ResponseEntity.of(optionalUser);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create")
    public User createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);

    }

    @PutMapping("/update")
    public User updateUser(@RequestBody UserDto user){
        if(Objects.nonNull(user.getId())){
            return userService.updateUser(user,user.getId().toString());
        }
        else {
            log.error("Exception thrown in controller");
            throw new InvalidUserException();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String userId){
        if(Objects.nonNull(userId)){
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else{
            log.error("Exception in controller");
            throw new InvalidUserException();
        }
    }


}
