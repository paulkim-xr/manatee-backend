package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper {
    Unit findById(Long id);
    UnitDto findWithCounts(Long id);
    List<Unit> findAll();
    List<UnitDto> findAllWithCounts();
    Unit getParent(Long id);
    List<Unit> getChildren(Long id);
    List<Employee> getEmployees(Long id);
    void insert(Unit unit);
    void update(Unit unit);
    void delete(Long id);
}
