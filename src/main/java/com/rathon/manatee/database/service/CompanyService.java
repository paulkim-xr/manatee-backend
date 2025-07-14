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
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
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

    @Override
    public void update(CompanyDto d) {
        UnitDto root = this.getRoot(d.getId());
        // TODO - BUG
        System.out.printf("Unit ID: %d%nUnit Name: %s%nCompany Name : %s%n", root.getId(), root.getName(), root.getCompany().name());
        root.setName(d.getName());
        System.out.printf("Unit ID: %d%nUnit Name: %s%nCompany Name : %s%n", root.getId(), root.getName(), root.getCompany().name());
        unitMapper.update(this.toUnit(root));
        mapper.update(service.toEntity(d));
    }

    @Override
    public void delete(Long id) throws Error {
        List<UnitDto> units = this.getUnits(id, true);
        if (units.size() > 1) {
            throw new Error();
        }

        if (units.size() == 1) {
            unitMapper.delete(units.getFirst().getId());
        }
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

    private Unit toUnit(UnitDto d) {
        Unit u = new Unit();
        u.setCompanyId(d.getCompany().id());
        u.setName(d.getName());
        u.setTypeId(d.getType().id());
        u.setCode(d.getCode());
        if (d.getParent() != null) {
            u.setParentId(d.getParent().id());
        }

        return u;
    }
}
