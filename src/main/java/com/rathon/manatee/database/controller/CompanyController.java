package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapperService cMapper;
    private final UnitMapperService uMapper;

    public CompanyController(CompanyService service, CompanyMapperService cMapper, UnitMapperService uMapper) {
        this.companyService = service;
        this.cMapper = cMapper;
        this.uMapper = uMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        Company c = companyService.getCompanyById(id);
        return (c == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(cMapper.toDto(c));
    }

    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAllCompanies().stream()
                .map(cMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/units")
    public List<UnitDto> getUnits(@PathVariable Long id) {
        return companyService.getUnits(id).stream()
                .map(uMapper::toDto)
                .collect(Collectors.toList());
    }
}
