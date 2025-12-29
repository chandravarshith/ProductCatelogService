package org.example.productcatelogservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseClass {
    @Id
    private Long id;
    private Date creationAt;
    private Date lastUpdateAt;
    private State state;
}
