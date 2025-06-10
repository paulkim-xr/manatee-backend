package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.mapper.CompanyMapper;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyMapper mapper;

    public CompanyService(CompanyMapper mapper) {
        this.mapper = mapper;
    }

    public Company getCompanyById(Long id) {
        return mapper.findById(id);
    }

    public CompanyDto getCompanyByIdWithCounts(Long id) {
        return mapper.findWithCounts(id);
    }

    public List<CompanyDto> getCompaniesWithCounts() {
        return mapper.findAllWithCounts();
    }

    public List<Company> getAllCompanies() {
        return mapper.findAll();
    }

    public List<Unit> getUnits(Long id) {
        return mapper.getUnits(id);
    }

    public List<Employee> getEmployees(Long id) {
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
}
