package com.rathon.manatee.database.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private Long id;
    private Long ownerId;
    private String username;
    private String passwordHash;
    private boolean active;
    private Date created;
    private Date lastActive;
}
