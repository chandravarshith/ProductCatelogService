package org.example.userauthenticationservice.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice.dtos.*;
import org.example.userauthenticationservice.exceptions.InvalidPasswordException;
import org.example.userauthenticationservice.exceptions.UserExistException;
import org.example.userauthenticationservice.exceptions.UserNotRegisteredException;
import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.AuthService;
import org.example.userauthenticationservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
            return new ResponseEntity<>(UserUtils.getUserDto(user), HttpStatus.OK);
        }
        catch(UserExistException ex){
            throw new UserExistException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try{
            Pair<User, String> response = this.authService.login(loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());
            String token = response.b;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth", token);
            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .body(UserUtils.getUserDto(response.a));
        }catch (UserNotRegisteredException ex){
            throw new UserNotRegisteredException(ex.getMessage());
        }catch (InvalidPasswordException ex){
            throw new InvalidPasswordException(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        Boolean isLoggedOut = this.authService.logout(logoutRequestDto.getUserId(),
                logoutRequestDto.getToken());
        if(isLoggedOut){
            return ResponseEntity.status(HttpStatus.OK).body("Logout Successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("Logout Failed, No Active Session");
        }
    }

    @PostMapping("/validateToken")
    private ResponseEntity<String> validateToken(
            @RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        Boolean isValid = this.authService.validateToken(validateTokenRequestDto.getToken(),
                validateTokenRequestDto.getUserId());
        if(isValid){
            return ResponseEntity.ok().body("Token validated successfully");
        }
        else  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
