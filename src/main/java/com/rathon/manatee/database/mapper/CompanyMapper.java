package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import com.rathon.manatee.database.model.Company;

import java.util.List;

@Mapper
public interface CompanyMapper {
    Company findById(Long id);
    CompanyDto findWithCounts(Long id);
    List<Company> findAll();
    List<CompanyDto> findAllWithCounts();
    List<Unit> getUnits(Long id);
    List<Employee> getEmployees(Long id);
    void insert(Company company);
    void update(Company company);
    void delete(Long id);

}
