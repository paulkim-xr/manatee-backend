package com.rathon.manatee.database.dto;

import com.rathon.manatee.core.dto.SearchField;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Industry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDto extends Dto<Company> {
    private String name;
    private String address;
    private Industry industry;
    private String registrationNumber;
    private Integer unitCount;
    private Integer employeeCount;

    public static List<SearchField> searchFields = List.of(
            new SearchField("name", "companies.name", String.class),
            new SearchField("address", "companies.address", String.class),
            new SearchField("industry", "industries.name", String.class),
            new SearchField("registrationNumber", "companies.registration_number", String.class)
    );
}
