package com.rathon.manatee.database.service;


import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    private final UnitMapper mapper;

    public UnitService(UnitMapper mapper) {
        this.mapper = mapper;
    }

    public Unit getUnitById(Long id) {
        return mapper.findById(id);
    }

    public List<Unit> getAllUnits() {
        return mapper.findAll();
    }

    public List<Employee> getEmployees(Long id) {
        return mapper.getEmployees(id);
    }

    public void createUnit(Unit u) {
        mapper.insert(u);
    }

    public void updateUnit(Unit u) {
        mapper.update(u);
    }

    public void deleteUnit(Long id) {
        mapper.delete(id);
    }
}
