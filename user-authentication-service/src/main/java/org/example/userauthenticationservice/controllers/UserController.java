package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.UserDto;
import org.example.userauthenticationservice.exceptions.UserNotRegisteredException;
import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.IUserService;
import org.example.userauthenticationservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.getUserDetails(id);
        if(user == null){
            throw new UserNotRegisteredException("User not Found");
        }
        return ResponseEntity.ok(UserUtils.getUserDto(user));
    }
}
