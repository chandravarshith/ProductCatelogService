package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserDetails(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            return null;
        return userOptional.get();
    }
}
