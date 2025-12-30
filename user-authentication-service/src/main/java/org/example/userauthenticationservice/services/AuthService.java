package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.InvalidPasswordException;
import org.example.userauthenticationservice.exceptions.UserExistException;
import org.example.userauthenticationservice.exceptions.UserNotRegisteredException;
import org.example.userauthenticationservice.models.Status;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User signup(String email,  String password, String name, String phoneNumber) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserExistException("User with this email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotRegisteredException("User not registered");
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }
        user.setStatus(Status.ACTIVE);
        // Generate JWT
        return userRepository.save(user);
    }
}
