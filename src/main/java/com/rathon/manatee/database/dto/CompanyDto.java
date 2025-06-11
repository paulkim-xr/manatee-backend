package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private Long id;
    private String name;
    private String address;
    private String industry;
    private String registrationNumber;
    private Integer unitCount;
    private Integer employeeCount;
}
