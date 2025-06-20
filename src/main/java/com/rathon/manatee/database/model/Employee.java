package com.rathon.manatee.database.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Long unitId;
    private Long positionId;
    private String email;
    private String phone;
    private Date dob;

    private String username;
    private String passwordHash;
}
