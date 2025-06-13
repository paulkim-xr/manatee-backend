package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Industry;
import com.rathon.manatee.database.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperService {
    @Autowired
    private IndustryService industryService;

    public CompanyDto toDto(Company c) {
        CompanyDto d = new CompanyDto();
        d.setId(c.getId());
        d.setName(c.getName());
        d.setAddress(c.getAddress());
        d.setIndustry(new Industry(c.getIndustryId(), industryService.getIndustryById(c.getIndustryId()).name()));
        d.setRegistrationNumber(c.getRegistrationNumber());

        return d;
    }

    public Company toEntity(CompanyDto d) {
        Company c = new Company();
        c.setId(d.getId());
        c.setName(d.getName());
        c.setAddress(d.getAddress());
        c.setIndustryId(d.getIndustry().id());
        c.setRegistrationNumber(d.getRegistrationNumber());

        return c;
    }
}
