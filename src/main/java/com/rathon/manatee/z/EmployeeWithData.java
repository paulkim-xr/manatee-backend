package com.rathon.manatee.z;

import org.springframework.data.rest.core.config.Projection;

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
