package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.UnitTypeDto;
import com.rathon.manatee.database.model.UnitType;
import org.springframework.stereotype.Component;

@Component
public class UnitTypeMapperService {
    public UnitTypeDto toDto(UnitType i) {
        return new UnitTypeDto(i.id(), i.name());
    }

    public UnitType toEntity(UnitTypeDto d) {
        return new UnitType(d.id(), d.name());
    }
}
