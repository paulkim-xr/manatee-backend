package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperService {
    public CompanyDto toDto(Company c) {
        CompanyDto d = new CompanyDto();
        d.setId(c.getId());
        d.setName(c.getName());
        d.setAddress(c.getAddress());
        d.setIndustryId(c.getIndustryId());
        d.setRegistrationNumber(c.getRegistrationNumber());

        return d;
    }

    public Company toEntity(CompanyDto d) {
        Company c = new Company();
        c.setId(d.getId());
        c.setName(d.getName());
        c.setAddress(d.getAddress());
        c.setIndustryId(d.getIndustryId());
        c.setRegistrationNumber(d.getRegistrationNumber());

        return c;
    }
}
