package com.rathon.manatee.database.dto;

import com.rathon.manatee.core.dto.SearchField;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.model.UnitType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public static List<SearchField> searchFields = List.of(
            new SearchField("name", "units.name", String.class),
            new SearchField("company", "companies.name", String.class),
            new SearchField("type", "unit_types.name", String.class),
            new SearchField("code", "units.code", String.class),
            new SearchField("parent", "parent.name", String.class)
    );
}
