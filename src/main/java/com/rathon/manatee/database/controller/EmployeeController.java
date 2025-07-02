package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedList;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.EmployeeService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapperService mapper;

    public EmployeeController(EmployeeService service, EmployeeMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public PagedList<EmployeeDto> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return service.getPagedEmployees(page, size, sort);
    }

    @GetMapping("/all")
    public List<EmployeeDto> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        EmployeeDto e = service.getEmployeeById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeDto d) {
        Employee e = mapper.toEntity(d);
        // TODO - add logic to check required fields
        service.createEmployee(e);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeDto d) {
        Employee c = mapper.toEntity(d);
        service.updateEmployee(c); // TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<EmployeeDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String dob,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        PagedList<EmployeeDto> pagedList = null;
        if (query != null) {
            pagedList = service.search(query, page, size, sort);
        } else {
            pagedList = service.search(company, unit, lastName, firstName, name, position, email, phone, dob, page, size, sort);
        }

        return ResponseEntity.ok(pagedList);
    }
}
