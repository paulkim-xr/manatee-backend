package com.rathon.manatee.database.mapper;

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
    List<EmployeeDto> getPagedEmployees(int offset, int size, String sortColumn, String sortDirection);

    Employee findByUsername(String username);

    Integer getCount();

    List<EmployeeDto> search(
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
}
