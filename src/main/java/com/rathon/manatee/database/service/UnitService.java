package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    private final UnitMapper mapper;

    public UnitService(UnitMapper mapper) {
        this.mapper = mapper;
    }

    public UnitDto getUnitById(Long id) {
        return mapper.findById(id);
    }

    public List<UnitDto> getUnitList() {
        return mapper.findAll();
    }

    public UnitDto getParent(Long id) {
        return mapper.getParent(id);
    }

    public List<UnitDto> getChildren(Long id) {
        return mapper.getChildren(id);
    }

    public List<EmployeeDto> getEmployees(Long id) {
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

    public List<UnitDto> searchAll(String query) {
        return mapper.searchAll(query);
    }

    public List<UnitDto> getFullUnitList() {
        return mapper.findAllFull();
    }
}
