package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.model.UnitType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitTypeMapper {
    UnitType findById(Long id);
    List<UnitType> findAll();
    void insert(UnitType unitType);
    void update(UnitType unitType);
    void delete(Long id);
}