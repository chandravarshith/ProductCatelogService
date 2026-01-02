package org.example.userauthenticationservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice.models.User;

public interface IAuthService {
    User signup(String email,  String password, String name, String phoneNumber);
    Pair<User, String> login(String email, String password);
    Boolean validateToken(String token, Long userId);

    Boolean logout(Long userId, String token);
}
