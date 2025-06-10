package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto {
    private Long id;
    private Long companyId;
    private String name;
    private Long typeId;
    private String code;
    private Long parentId;
    private Integer employeeCount;
}
