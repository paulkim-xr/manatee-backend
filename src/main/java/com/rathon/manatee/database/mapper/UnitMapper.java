package com.rathon.manatee.database.mapper;

import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper extends ObjectMapper<Unit, UnitDto> {
    List<UnitDto> findAllDto(Boolean root);

    UnitDto getParent(Long id);
    List<UnitDto> getChildren(Long id);
    List<EmployeeDto> getEmployees(Long id);

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
    Integer searchCount(String name, String company, String type, String code, String parent);
}
