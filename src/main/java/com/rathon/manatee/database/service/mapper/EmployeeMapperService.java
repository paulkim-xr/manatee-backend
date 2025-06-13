package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.IdNameDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.PositionService;
import com.rathon.manatee.database.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private PositionService positionService;

    public EmployeeDto toDto(Employee e) {
        EmployeeDto d = new EmployeeDto();
        UnitDto u = unitService.getUnitById(e.getUnitId());
        d.setCompany(new IdNameDto(u.getCompany().id(), companyService.getCompanyById(u.getCompany().id()).getName()));
        d.setId(e.getId());
        d.setDob(e.getDob());
        d.setEmail(e.getEmail());
        d.setPhone(e.getPhone());
        d.setLastName(e.getLastName());
        d.setFirstName(e.getFirstName());
//        d.setUnit();
        d.setUnit(new IdNameDto(u.getId(), u.getName()));
        d.setPosition(positionService.getPositionById(e.getPositionId()));

        return d;
    }

    public Employee toEntity(EmployeeDto d) {
        Employee e = new Employee();
        e.setId(d.getId());
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
//        e.setUnitId(d.getUnit().getId());
        e.setUnitId(d.getUnit().id());
        e.setPositionId(d.getPosition().id());
        e.setEmail(d.getEmail());
        e.setPhone(d.getPhone());
        e.setDob(d.getDob());

        return e;
    }
}
