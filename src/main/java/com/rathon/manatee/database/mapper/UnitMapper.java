package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper {
    UnitDto findById(Long id);
    List<UnitDto> findAll();
    UnitDto getParent(Long id);
    List<UnitDto> getChildren(Long id);
    List<EmployeeDto> getEmployees(Long id);
    void insert(Unit unit);
    void update(Unit unit);
    void delete(Long id);
    List<UnitDto> searchAll(String query);

    List<UnitDto> findAllFull();
    List<UnitDto> getPagedUnits(int offset, int size, String sortColumn, String sortDirection);

    Integer getCount();

    Integer getSearchCount(String name, String company, String type, String code, String parent);

    List<UnitDto> search(
            String name,
            String company,
            String type,
            String code,
            String parent,
            String sortColumn,
            String sortDirection,
            Integer offset,
            Integer size
    );
}
