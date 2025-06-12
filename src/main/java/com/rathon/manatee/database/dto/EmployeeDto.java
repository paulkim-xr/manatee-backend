package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String companyName;
    private String unitName ;
    private String firstName;
    private String lastName;
    private String position;
    private String email;
    private String phone;
    private Date dob;
}
