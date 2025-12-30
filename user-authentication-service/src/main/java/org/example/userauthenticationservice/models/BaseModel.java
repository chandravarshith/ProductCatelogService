package org.example.userauthenticationservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Date createdAt;
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public BaseModel() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
