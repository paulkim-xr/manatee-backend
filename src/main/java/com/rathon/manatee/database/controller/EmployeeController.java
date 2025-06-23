package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedDtoList;
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

    @GetMapping("/all")
    public List<EmployeeDto> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping
    public PagedDtoList<EmployeeDto> getPaged(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        List<EmployeeDto> list = service.getPagedEmployees(page * size, size);
        PagedDtoList<EmployeeDto> pagedList = new PagedDtoList<>();
        Integer totalCount = service.getCount();
        pagedList.setContent(list);
        pagedList.setPage(page);
        pagedList.setSize(size);
        pagedList.setTotalCount(totalCount);
        pagedList.setTotalPages(Math.ceilDiv(totalCount, size));
        pagedList.setFirst(page == 0);
        pagedList.setLast(page.equals(totalCount - 1));

        return pagedList;
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto d) {
        Employee c = mapper.toEntity(d);
        c.setId(id);
        service.updateEmployee(c); // TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> search(@RequestParam String query) {
        return ResponseEntity.ok(service.searchAll(query));
    }
}
