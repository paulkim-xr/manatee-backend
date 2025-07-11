package com.rathon.manatee.database.service;

import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService extends ObjectService<Unit, UnitDto, UnitMapper, UnitMapperService> {
    private final EmployeeMapper employeeMapper;

    public UnitService(
            UnitMapper mapper,
            UnitMapperService service,
            EmployeeMapper employeeMapper
    ) {
        super(mapper, service);
        this.employeeMapper = employeeMapper;
    }

    @Override
    public UnitDto getObjectById(Long id) {
        return mapper.findByIdDto(id);
    }

    @Override
    public PagedList<UnitDto> getPagedObjects(int page, int size, String sort) {
        SortInfo sortInfo = new SortInfo(sort);
        List<UnitDto> list = mapper.getPagedObjectsDto(page * size, size, sortInfo.column, sortInfo.direction);

        return PagedList.build(list, page, size, getCount());
    }

    public List<UnitDto> getAll(Boolean root) {
        return mapper.findAllDto(root);
    }

    public UnitDto getParent(Long id) {
        return mapper.findParent(id);
    }

    public List<UnitDto> getChildren(Long id) {
        return mapper.findChildren(id);
    }

    public List<EmployeeDto> getEmployees(Long id) {
        return employeeMapper.findUnitEmployeesDto(id);
    }

    @Override
    public void delete(Long id) {
        // Implement check logic
        mapper.delete(id);
    }

    public PagedList<UnitDto> search(String query, Integer page, Integer size, String sort) {
        return search(query, query, query, query, query, page, size, sort);
    }

    public PagedList<UnitDto> search(
            String name,
            String company,
            String type,
            String code,
            String parent,
            Integer page,
            Integer size,
            String sort
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<UnitDto> list = mapper.searchDto(name, company, type, code, parent, sortInfo.column, sortInfo.direction, page * size, size);

        return PagedList.build(list, page, size, mapper.searchCountDto(name, company, type, code, parent));
    }

    public Boolean checkUniqueCode(String code) {
        return mapper.checkUniqueCode(code);
    }

    public List<UnitDto> getRoots() {
        return mapper.findRootUnitsDto();
    }
}
