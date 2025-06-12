package com.rathon.manatee.database.service;

import com.rathon.manatee.database.mapper.UnitTypeMapper;
import com.rathon.manatee.database.model.UnitType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitTypeService {
    private final UnitTypeMapper mapper;

    public UnitTypeService(UnitTypeMapper mapper) {
        this.mapper = mapper;
    }

    public UnitType getUnitTypeById(Long id) {
        return this.mapper.findById(id);
    }

    public List<UnitType> getAll() {
        return this.mapper.findAll();
    }

    public void createUnitType(UnitType u) {
        mapper.insert(u);
    }

    public void updateUnitType(UnitType u) {
        mapper.update(u);
    }

    public void deleteUnitType(Long id) {
        mapper.delete(id);
    }
}
