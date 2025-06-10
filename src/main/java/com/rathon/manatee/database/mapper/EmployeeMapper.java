package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.model.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee findById(Long id);
    List<Employee> findAll();
    List<Employee> findByFirstname(String firstName);
    List<Employee> findByLastname(String lastName);
    void insert(Employee e);
    void update(Employee e);
    void delete(Long id);
}
