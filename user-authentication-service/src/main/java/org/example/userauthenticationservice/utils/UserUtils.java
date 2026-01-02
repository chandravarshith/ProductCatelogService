package org.example.userauthenticationservice.utils;

import org.example.userauthenticationservice.dtos.UserDto;
import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.models.User;

import java.util.ArrayList;

public class UserUtils {
    public static UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(new ArrayList<>());
        for(Role role : user.getRoles()){
            userDto.getRoles().add(role);
        }
        return userDto;
    }
}
