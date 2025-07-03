package com.rathon.manatee.database.dto;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto extends Dto<Employee> {
    private Long id;
    private IdNameDto company;
    private IdNameDto unit;
    private String firstName;
    private String lastName;
    private String username;
    private Position position;
    private String email;
    private String phone;
    private Date dob;
}
