package com.rathon.manatee.database.service;

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

    public Employee getEmployeeById(Long id) {
        return mapper.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return mapper.findAll();
    }

    public List<Employee> findByFirstName(String firstName) {
        return mapper.findByFirstname(firstName);
    }

    public List<Employee> findByLastName(String lastName) {
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
}
