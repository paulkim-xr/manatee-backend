package com.rathon.manatee;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "withData", types = Company.class)
public interface CompanyWithData {
    public Long getId();
    public String getName();
    public String getAddress();
    public String getIndustry();
    public String getRegistrationNumber();
    public List<OrgUnit> getOrgUnits();
}
