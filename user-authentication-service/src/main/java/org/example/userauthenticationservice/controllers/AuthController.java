package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.LoginRequestDto;
import org.example.userauthenticationservice.dtos.SignupRequestDto;
import org.example.userauthenticationservice.dtos.UserDto;
import org.example.userauthenticationservice.exceptions.InvalidPasswordException;
import org.example.userauthenticationservice.exceptions.UserExistException;
import org.example.userauthenticationservice.exceptions.UserNotRegisteredException;
import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try{
            User user = this.authService.signup(
                    signupRequestDto.getEmail(),
                    signupRequestDto.getPassword(),
                    signupRequestDto.getName(),
                    signupRequestDto.getPhoneNumber()
            );
            return new ResponseEntity<>(from(user), HttpStatus.OK);
        }
        catch(UserExistException ex){
            throw new UserExistException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try{
            User user = this.authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            return new ResponseEntity<>(from(user), HttpStatus.OK);
        }catch (UserNotRegisteredException ex){
            throw new UserNotRegisteredException(ex.getMessage());
        }catch (InvalidPasswordException ex){
            throw new InvalidPasswordException(ex.getMessage());
        }
    }

//    public ResponseEntity<>

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus().name());
        userDto.setRoles(new ArrayList<>());
        for(Role role : user.getRoles()){
            userDto.getRoles().add(role);
        }
        return userDto;
    }
}
