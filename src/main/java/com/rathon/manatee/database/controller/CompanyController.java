package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService cService;
    private final UnitService uService;
    private final CompanyMapperService cMapper;

    public CompanyController(CompanyService cService, UnitService uService, CompanyMapperService cMapper) {
        this.cService = cService;
        this.uService = uService;
        this.cMapper = cMapper;
    }

    @GetMapping
    public List<CompanyDto> getAll() {
        return cService.getCompanyList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        if (cService.getCompanyById(id) == null) return ResponseEntity.notFound().build();
        CompanyDto c = cService.getCompanyById(id);
        return (c == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(c);
    }

    @GetMapping("/{id}/units")
    public ResponseEntity<List<UnitDto>> getUnits(@PathVariable Long id) {
        if (cService.getCompanyById(id) == null) return ResponseEntity.badRequest().build();

        List<UnitDto> list =  cService.getUnits(id);

        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDto> getEmployees(@PathVariable Long id) {
        return cService.getEmployees(id);
    }

    @GetMapping("/{id}/root")
    public ResponseEntity<UnitDto> getRoot(@PathVariable Long id) {
        return ResponseEntity.ok(cService.getRoot(id));
    }

    @GetMapping("/{id}/full-units")
    public ResponseEntity<List<UnitDto>> fullUnits(@PathVariable Long id) {
        return ResponseEntity.ok(cService.getUnitsFull(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCompany(@RequestBody CompanyDto d) {
        Company c = cMapper.toEntity(d);
        cService.createCompany(c);
        Unit u = new Unit();
        u.setCompanyId(c.getId());
        u.setName(c.getName());
        u.setTypeId(1L);
        uService.createUnit(u);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable Long id, @RequestBody CompanyDto d) {
        Company c = cMapper.toEntity(d);
        c.setId(id);
        cService.updateCompany(c); // TODO
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        List<UnitDto> units = cService.getUnits(id);
        if (units.size() > 1) {
            return ResponseEntity.badRequest().body("Remove all units to delete");
        }

        if (units.size() == 1) {
            uService.deleteUnit(units.getFirst().getId());
        }

        cService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDto>> search(@RequestParam String query) {
        return ResponseEntity.ok(cService.searchAll(query));
    }
}
