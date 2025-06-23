package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.model.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeDto findById(Long id);
    List<EmployeeDto> findAll();
    List<EmployeeDto> findByFirstname(String firstName);
    List<EmployeeDto> findByLastname(String lastName);
    void insert(Employee e);
    void update(Employee e);
    void delete(Long id);
    List<EmployeeDto> searchAll(String query);
    List<EmployeeDto> getPagedEmployees(int offset, int size);

    Employee findByUsername(String username);

    Integer getCount();
}
