package com.rathon.manatee.z;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "withData", types = OrgUnit.class)
public interface OrgUnitWithData {
    Long getId();
    String getName();
    String getType();
    String getCode();
    Company getCompany();
    OrgUnit getParent();
    List<OrgUnit> getChildren();
    List<Employee> getEmployees();
}
