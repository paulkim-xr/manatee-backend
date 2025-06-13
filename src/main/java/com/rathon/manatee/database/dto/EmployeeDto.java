package com.rathon.manatee.database.dto;

import com.rathon.manatee.database.model.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private IdNameDto company;
    private IdNameDto unit;
    private String firstName;
    private String lastName;
    private Position position;
    private String email;
    private String phone;
    private Date dob;
}
