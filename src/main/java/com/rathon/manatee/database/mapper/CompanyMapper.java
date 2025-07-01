package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import org.apache.ibatis.annotations.Mapper;

import com.rathon.manatee.database.model.Company;

import java.util.List;

@Mapper
public interface CompanyMapper {
    CompanyDto findById(Long id);
    List<CompanyDto> findAll();
    List<UnitDto> getUnits(Long id);
    List<EmployeeDto> getEmployees(Long id);
    void insert(Company company);
    void update(Company company);
    void delete(Long id);

    List<UnitDto> getUnitsFull();

    UnitDto getRoot(Long id);

    List<CompanyDto> getPagedCompanies(int offset, int size, String sortColumn, String sortDirection);

    Integer getCount();

    List<CompanyDto> search(String name, String address, String industry, String registrationNumber, String sortColumn, String sortDirection, int offset, int size);

    Integer getSearchCount(String name, String address, String industry, String registrationNumber);
}
