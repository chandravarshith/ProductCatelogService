package org.example.userauthenticationservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    private String token;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
