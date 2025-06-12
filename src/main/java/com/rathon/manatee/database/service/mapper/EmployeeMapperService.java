package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperService {
    public EmployeeDto toDto(Employee e) {
        EmployeeDto d = new EmployeeDto();
        d.setId(e.getId());
        d.setDob(e.getDob());
        d.setEmail(e.getEmail());
        d.setPhone(e.getPhone());
        d.setLastName(e.getLastName());
        d.setFirstName(e.getFirstName());
//        d.setUnitId(e.getUnitId());
//        d.setPositionId(e.getPositionId());

        return d;
    }

    public Employee toEntity(EmployeeDto d) {
        Employee e = new Employee();
        e.setId(d.getId());
        e.setDob(d.getDob());
        e.setEmail(d.getEmail());
        e.setPhone(d.getPhone());
        e.setLastName(d.getLastName());
        e.setFirstName(d.getFirstName());
//        e.setUnitId(d.getUnitId());
//        e.setPositionId(d.getPositionId());

        return e;
    }
}
