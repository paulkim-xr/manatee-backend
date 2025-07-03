package com.rathon.manatee.database.mapper;

import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.model.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper extends ObjectMapper<Employee, EmployeeDto> {
    Employee findByUsername(String username);

    List<EmployeeDto> searchDto(
            String company,
            String unit,
            String lastName,
            String firstName,
            String name,
            String position,
            String email,
            String phone,
            String dob,
            String sortColumn,
            String sortDirection,
            Integer offset,
            Integer size);

    Integer searchCount(
            String company,
            String unit,
            String lastName,
            String firstName,
            String name,
            String position,
            String email,
            String phone,
            String dob
    );
}
