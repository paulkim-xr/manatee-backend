package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto {
    private Long id;
    private String companyName;
    private String name;
    private String type;
    private String code;
    private String parentName;
    private Integer employeeCount;
}
