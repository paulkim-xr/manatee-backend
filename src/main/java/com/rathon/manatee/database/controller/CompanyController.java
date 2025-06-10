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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService cService;
    private final UnitService uService;
    private final CompanyMapperService cMapper;
    private final UnitMapperService uMapper;
    private final EmployeeMapperService eMapper;

    public CompanyController(CompanyService cService, UnitService uService, CompanyMapperService cMapper, UnitMapperService uMapper, EmployeeMapperService eMapper) {
        this.cService = cService;
        this.uService = uService;
        this.cMapper = cMapper;
        this.uMapper = uMapper;
        this.eMapper = eMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        if (cService.getCompanyById(id) == null) return ResponseEntity.notFound().build();
        CompanyDto c = cService.getCompanyByIdWithCounts(id);
        return (c == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(c);
    }

    @GetMapping("/")
    public List<CompanyDto> getAll() {
        return cService.getCompaniesWithCounts();
    }

    @GetMapping("/{id}/units")
    public ResponseEntity<List<UnitDto>> getUnits(@PathVariable Long id) {
        if (cService.getCompanyById(id) == null) return ResponseEntity.badRequest().build();

        List<UnitDto> list =  cService.getUnits(id).stream()
                .map(uMapper::toDto)
                .toList();

        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDto> getEmployees(@PathVariable Long id) {
        return cService.getEmployees(id).stream()
                .map(eMapper::toDto)
                .collect(Collectors.toList());
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
        List<Unit> units = cService.getUnits(id);
        if (units.size() > 1) {
            return ResponseEntity.badRequest().body("Remove all units to delete");
        }

        uService.deleteUnit(units.getFirst().getId());
        cService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
