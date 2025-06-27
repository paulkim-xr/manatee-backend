package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public EmployeeDto getEmployeeById(Long id) {
        return mapper.findById(id);
    }

    public List<EmployeeDto> getAllEmployees() {
        return mapper.findAll();
    }

    public List<EmployeeDto> findByFirstName(String firstName) {
        return mapper.findByFirstname(firstName);
    }

    public List<EmployeeDto> findByLastName(String lastName) {
        return mapper.findByLastname(lastName);
    }

    public void createEmployee(Employee e) {
        mapper.insert(e);
    }

    public void updateEmployee(Employee e) {
        mapper.update(e);
    }

    public void deleteEmployee(Long id) {
        mapper.delete(id);
    }

    public List<EmployeeDto> searchAll(String query) {
        return mapper.searchAll(query);
    }

    public Employee getEmployeeByUsername(String username) {
        return mapper.findByUsername(username);
    }

    public PagedDtoList<EmployeeDto> getPagedEmployees(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<EmployeeDto> list = mapper.getPagedEmployees(page * size, size, sortColumn, sortDirection);
        return toPagedDto(list, page, size);
    }

    public Integer getCount() {
        return mapper.getCount();
    }

    public PagedDtoList<EmployeeDto> search(String query, Integer page, Integer size, String sort) {
        return search(query, query, query, query, query, query, query, query, query, page, size, sort);
    }

    public PagedDtoList<EmployeeDto> search(
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
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<EmployeeDto> list = mapper.search(company, unit,
                lastName,
                firstName,
                name,
                position,
                email,
                phone,
                dob,
                sortColumn,
                sortDirection,
                page * size,
                size);

        return toPagedDto(list, page, size);
    }

    private PagedDtoList<EmployeeDto> toPagedDto(List<EmployeeDto> list, int page, int size) {
        PagedDtoList<EmployeeDto> pagedList = new PagedDtoList<>();
        int totalCount = list.size();
        pagedList.setContent(list);
        pagedList.setPage(page);
        pagedList.setSize(size);
        pagedList.setTotalCount(totalCount);
        pagedList.setTotalPages(Math.ceilDiv(totalCount, size));
        pagedList.setFirst(page == 0);
        pagedList.setLast(page == (pagedList.getTotalPages() - 1));

        return pagedList;
    }
}
