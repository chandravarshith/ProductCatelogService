package org.example.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthenticationservice.models.Role;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String status;
    private List<Role> roles;
}
