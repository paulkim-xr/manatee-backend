package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.CompanyMapper;
import com.rathon.manatee.database.model.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyMapper mapper;

    public CompanyService(CompanyMapper mapper) {
        this.mapper = mapper;
    }

    public CompanyDto getCompanyById(Long id) {
        return mapper.findById(id);
    }

    public List<CompanyDto> getCompanyList() {
        return mapper.findAll();
    }

    public List<UnitDto> getUnits(Long id) {
        return mapper.getUnits(id);
    }

    public List<EmployeeDto> getEmployees(Long id) {
        return mapper.getEmployees(id);
    }

    public void createCompany(Company c) {
        mapper.insert(c);
    }

    public void updateCompany(Company c) {
        mapper.update(c);
    }

    public void deleteCompany(Long id) {
        mapper.delete(id);
    }

    public List<CompanyDto> searchAll(String query) {
        return mapper.searchAll(query);
    }

    public List<UnitDto> getUnitsFull(Long id) {
        return mapper.getUnitsFull();
    }

    public UnitDto getRoot(Long id) {

        return mapper.getRoot(id);
    }

    public List<CompanyDto> getPagedCompanies(int offset, int size) {
        return mapper.getPagedCompanies(offset, size);
    }
}
