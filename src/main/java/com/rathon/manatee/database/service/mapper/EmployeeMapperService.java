package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.PositionService;
import com.rathon.manatee.database.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperService implements ObjectMapperService<Employee, EmployeeDto> {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private PositionService positionService;

    @Override
    public EmployeeDto toDto(Employee e) {
        EmployeeDto d = new EmployeeDto();
        UnitDto u = unitService.getObjectById(e.getUnitId());
        d.setCompany(new IdNameDto(u.getCompany().id(), companyService.getObjectById(u.getCompany().id()).getName()));
        d.setId(e.getId());
        d.setDob(e.getDob());
        d.setEmail(e.getEmail());
        d.setPhone(e.getPhone());
        d.setLastName(e.getLastName());
        d.setFirstName(e.getFirstName());
        d.setUsername(e.getUsername());
        d.setUnit(new IdNameDto(u.getId(), u.getName()));
        d.setPosition(positionService.getPositionById(e.getPositionId()));

        return d;
    }

    @Override
    public Employee toEntity(EmployeeDto d) {
        Employee e = new Employee();
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setUnitId(d.getUnit().id());
        e.setPositionId(d.getPosition().id());
        e.setUsername(d.getUsername());
        e.setEmail(d.getEmail());
        e.setPhone(d.getPhone());
        e.setDob(d.getDob());

        return e;
    }
}
