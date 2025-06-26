package com.rathon.manatee.database.dto;

import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Industry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends ObjectDto<Company> {
//    private Long id;
    private String name;
    private String address;
    private Industry industry;
    private String registrationNumber;
    private Integer unitCount;
    private Integer employeeCount;
}
