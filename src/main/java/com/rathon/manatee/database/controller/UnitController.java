package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    private final UnitService uService;
    private final UnitMapperService uMapper;
    private final EmployeeMapperService eMapper;

    public UnitController(UnitService uService, UnitMapperService uMapper, EmployeeMapperService eMapper) {
        this.uService = uService;
        this.uMapper = uMapper;
        this.eMapper = eMapper;
    }

    @GetMapping("/")
    public List<UnitDto> getAll() {
        return uService.getUnitsWithCounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getById(@PathVariable Long id) {
        if (uService.getUnitById(id) == null) return ResponseEntity.notFound().build();
        UnitDto u = uService.getUnitByIdWithCounts(id);
        return (u == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(u);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@PathVariable Long id) {
        if (uService.getEmployees(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(uService.getEmployees(id).stream().map(eMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<Void> createUnit(@RequestBody UnitDto d) {
        Unit u = uMapper.toEntity(d);
        uService.createUnit(u);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUnit(@PathVariable Long id, @RequestBody UnitDto d) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable Long id) {
        List<Employee> list = uService.getEmployees(id);
        if (list.size() > 1) return ResponseEntity.badRequest().body("Remove all employees to delete");

        List<Unit> children = uService.getChildren(id);
        if (!children.isEmpty()) return ResponseEntity.badRequest().body("Remove all child units to delete");

        uService.deleteUnit(id);
        return ResponseEntity.ok().build();
    }
}
