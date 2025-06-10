package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long unitId ;
    private Long positionId;
    private String email;
    private String phone;
    private Date dob;
}
