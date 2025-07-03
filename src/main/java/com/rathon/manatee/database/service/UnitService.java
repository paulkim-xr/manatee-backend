package com.rathon.manatee.database.service;

import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService extends ObjectService<Unit, UnitDto, UnitMapper, UnitMapperService> {

    public UnitService(UnitMapper mapper, UnitMapperService service) {
        super(mapper, service);
    }

    @Override
    public UnitDto getObjectById(Long id) {
        return mapper.findByIdDto(id);
    }

    public List<UnitDto> getAll(Boolean root) {
        return mapper.findAllDto(root);
    }

    public UnitDto getParent(Long id) {
        return mapper.getParent(id);
    }

    public List<UnitDto> getChildren(Long id) {
        return mapper.getChildren(id);
    }

    public List<EmployeeDto> getEmployees(Long id) {
        return mapper.getEmployees(id);
    }

    public void insert(Unit u) {
        mapper.insert(u);
    }

    public void update(Unit u) {
        mapper.update(u);
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
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<UnitDto> list = mapper.search(name, company, type, code, parent, sortColumn, sortDirection, page * size, size);

        return PagedList.build(list, page, size, mapper.searchCount(name, company, type, code, parent));
    }
}
