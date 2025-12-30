package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.models.User;

public interface IAuthService {
    User signup(String email,  String password, String name, String phoneNumber);
    User login(String email, String password);
}
