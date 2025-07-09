package com.rathon.manatee.database.service;

import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.CompanyMapper;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends ObjectService<Company, CompanyDto, CompanyMapper, CompanyMapperService> {
    private final UnitMapper unitMapper;
    private final EmployeeMapper employeeMapper;

    public CompanyService(
            CompanyMapper mapper,
            CompanyMapperService service,
            UnitMapper unitMapper,
            EmployeeMapper employeeMapper
    ) {
        super(mapper, service);
        this.unitMapper = unitMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public CompanyDto getObjectById(Long id) {
        return mapper.findByIdDto(id);
    }

    @Override
    public List<CompanyDto> getAll() {
        return mapper.findAllDto();
    }

    @Override
    public PagedList<CompanyDto> getPagedObjects(int page, int size, String sort) {
        SortInfo sortInfo = new SortInfo(sort);

        List<CompanyDto> list = mapper.getPagedObjectsDto(page * size, size, sortInfo.column, sortInfo.direction);
        return PagedList.build(list, page, size, getCount());
    }

    public UnitDto getRoot(Long id) {
        return unitMapper.findCompanyRootUnitDto(id);
    }

    public List<UnitDto> getUnits(Long id, Boolean root) {
        return unitMapper.findCompanyUnitsDto(id, root);
    }

    public List<EmployeeDto> getEmployees(Long id) {
        return employeeMapper.findCompanyEmployeesDto(id);
    }

    public void insert(Company c) {
        mapper.insert(c);
    }

    public void update(Company c) {
        mapper.update(c);
    }

    @Override
    public void delete(Long id) {
        // TODO - RECURSIVE DELETE OR THROW ERROR WHEN CHILDREN EXIST?
        mapper.delete(id);
    }

    public PagedList<CompanyDto> search(
            String query,
            int page,
            int size,
            String sort
    ) {
        return search(query, query, query, query, page, size, sort);
    }

    public PagedList<CompanyDto> search(
            String name,
            String address,
            String industry,
            String registrationNumber,
            int page,
            int size,
            String sort
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<CompanyDto> list = mapper.searchDto(name, address, industry, registrationNumber, sortInfo.column, sortInfo.direction, page * size, size);

        return PagedList.build(list, page, size, mapper.searchCountDto(name, address, industry, registrationNumber));
    }
}
