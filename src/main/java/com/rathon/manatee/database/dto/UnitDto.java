package com.rathon.manatee.database.dto;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.model.UnitType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto extends Dto<Unit> {
    private Long id;
    private IdNameDto company;
    private String name;
    private UnitType type;
    private String code;
    private IdNameDto parent;
    private Integer childrenCount;
    private Integer employeeCount;
}
