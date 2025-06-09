package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitMapperService {
    public UnitDto toDto(Unit u) {
        UnitDto d = new UnitDto();
        d.setId(u.getId());
        d.setName(u.getName());
        d.setCode(u.getCode());
        d.setCompanyId(u.getCompanyId());
        d.setTypeId(u.getTypeId());
        d.setParentId(u.getParentId());

        return d;
    }

    public Unit toEntity(UnitDto d) {
        Unit u = new Unit();
        u.setId(d.getId());
        u.setName(d.getName());
        u.setCode(d.getCode());
        u.setCompanyId(d.getCompanyId());
        u.setTypeId(d.getTypeId());
        u.setParentId(d.getParentId());

        return u;
    }
}
