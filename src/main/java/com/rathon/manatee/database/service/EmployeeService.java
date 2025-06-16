package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public EmployeeDto getEmployeeById(Long id) {
        return mapper.findById(id);
    }

    public List<EmployeeDto> getAllEmployees() {
        return mapper.findAll();
    }

    public List<EmployeeDto> findByFirstName(String firstName) {
        return mapper.findByFirstname(firstName);
    }

    public List<EmployeeDto> findByLastName(String lastName) {
        return mapper.findByLastname(lastName);
    }

    public void createEmployee(Employee e) {
        mapper.insert(e);
    }

    public void updateEmployee(Employee e) {
        mapper.update(e);
    }

    public void deleteEmployee(Long id) {
        mapper.delete(id);
    }

    public List<EmployeeDto> searchAll(String query) {
        return mapper.searchAll(query);
    }
}
