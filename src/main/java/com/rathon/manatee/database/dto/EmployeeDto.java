package com.rathon.manatee.database.dto;

import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.core.dto.SearchField;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeDto extends Dto<Employee> {
    private IdNameDto company;
    private IdNameDto unit;
    private String name;
    private String username;
    private Position position;
    private String email;
    private String phone;
    private Date dob;
    private Boolean otpEnabled;
    private Boolean bioEnabled;
//    private List<UserGroup> groups;

    public static List<SearchField> searchFields = List.of(
            new SearchField("company", "companies.name", String.class),
            new SearchField("unit", "units.name", String.class),
            new SearchField("name", "employees.name", String.class),
            new SearchField("position", "positions.name", String.class),
            new SearchField("email", "employees.name", String.class),
            new SearchField("phone", "employees.phone", String.class),
            new SearchField("dob", "employees.dob", Date.class)
    );
}
