package com.rathon.manatee.database.service;

import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends ObjectService<Employee, EmployeeDto, EmployeeMapper, EmployeeMapperService> {

    public EmployeeService(EmployeeMapper mapper, EmployeeMapperService service) {
        super(mapper, service);
    }

    @Override
    public EmployeeDto getObjectById(Long id) {
        return mapper.findByIdDto(id);
    }

    @Override
    public List<EmployeeDto> getAll() {
        return mapper.findAllDto();
    }

    public void insert(Employee e) {
        mapper.insert(e);
    }

    public void update(Employee e) {
        mapper.update(e);
    }

    public Employee findByUsername(String name) {
        return mapper.findByUsername(name);
    }

    public PagedList<EmployeeDto> getPagedEmployees(int page, int size, String sort) {
        SortInfo sortInfo = new SortInfo(sort);

        List<EmployeeDto> list = mapper.getPagedObjectsDto(page * size, size, sortInfo.column, sortInfo.direction);
        return PagedList.build(list, page, size, getCount());
    }

    public PagedList<EmployeeDto> search(String query, Integer page, Integer size, String sort) {
        return search(query, query, query, query, query, query, query, query, query, page, size, sort);
    }

    public PagedList<EmployeeDto> search(
            String company,
            String unit,
            String lastName,
            String firstName,
            String name,
            String position,
            String email,
            String phone,
            String dob,
            Integer page, Integer size, String sort
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<EmployeeDto> list = mapper.searchDto(
                company,
                unit,
                lastName,
                firstName,
                name,
                position,
                email,
                phone,
                dob,
                sortInfo.column,
                sortInfo.direction,
                page * size,
                size);

        return PagedList.build(list, page, size, mapper.searchCountDto(
                company,
                unit,
                lastName,
                firstName,
                name,
                position,
                email,
                phone,
                dob
        ));
    }
}
