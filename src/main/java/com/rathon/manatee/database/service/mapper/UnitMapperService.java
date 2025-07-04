package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.UnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitMapperService implements ObjectMapperService<Unit, UnitDto> {
    @Autowired
    private CompanyService companyService;

//    @Autowired
//    private UnitService unitService;

    @Autowired
    private UnitTypeService unitTypeService;

    @Override
    public UnitDto toDto(Unit u) {
        UnitDto d = new UnitDto();
        d.setId(u.getId());
        d.setCompany(new IdNameDto(u.getCompanyId(), companyService.getObjectById(u.getCompanyId()).getName()));
        d.setName(u.getName());
        d.setType(unitTypeService.getUnitTypeById(u.getTypeId()));
        d.setCode(u.getCode());
//        d.setChildrenCount(unitService.getChildren(u.getId()).size());
        d.setChildrenCount(0); // set it in service
//        d.setParent(getAncestor(u.getParentId()));
        d.setParent(new IdNameDto(u.getParentId(), "")); // set it in service

        return d;
    }

//    private IdNameDto getAncestor(Long id) {
//        UnitDto unit = unitService.getObjectById(id);
//        return unit != null
//                ? new IdNameDto(id, unit.getName())
////                ? new IdNameDto(id, unit.getName(), getAncestor(unit.getParent().id()))
//                : null;
//    }

    @Override
    public Unit toEntity(UnitDto d) {
        Unit u = new Unit();
        u.setCompanyId(d.getCompany().id());
        u.setName(d.getName());
        u.setTypeId(d.getType().id());
        u.setCode(d.getCode());
        u.setParentId(d.getParent().id());

        return u;
    }
}
