package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private Long unitId ;
    private String firstName;
    private String lastName;
    private Long positionId;
    private String email;
    private String phone;
    private Date dob;
}
