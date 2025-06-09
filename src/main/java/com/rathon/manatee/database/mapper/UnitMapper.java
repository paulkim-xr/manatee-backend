package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper {
    Unit findById(Long id);
    List<Unit> findAll();
    List<Employee> getEmployees(Long id);
    void insert(Unit unit);
    void update(Unit unit);
    void delete(Long id);
}
