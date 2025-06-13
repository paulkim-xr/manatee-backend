package com.rathon.manatee.database.dto;

import com.rathon.manatee.database.model.UnitType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto {
    private Long id;
    private IdNameDto company;
    private String name;
    private UnitType type;
    private String code;
    private IdNameDto parent;
    private Integer employeeCount;
}
