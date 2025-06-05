package com.rathon.manatee;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "withData", types = Employee.class)
public interface EmployeeWithData {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    Position getPosition();
    OrgUnit getOrgUnit();
    Company getCompany();
}
