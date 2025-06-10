package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.EmployeeService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService eService;
    private final EmployeeMapperService eMapper;

    public EmployeeController(EmployeeService eService, EmployeeMapperService eMapper) {
        this.eService = eService;
        this.eMapper = eMapper;
    }

    @GetMapping("/")
    public List<EmployeeDto> getAll() {
        return eService.getAllEmployees().stream()
                .map(eMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        Employee e = eService.getEmployeeById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(eMapper.toDto(e));
    }

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeDto d) {
        Employee e = eMapper.toEntity(d);
        // TODO - add logic to check required fields
        eService.createEmployee(e);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto d) {
        Employee c = eMapper.toEntity(d);
        c.setId(id);
        eService.updateEmployee(c); // TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        eService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
