package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.UnitMapper;
import com.rathon.manatee.database.model.Unit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UnitService {
    private final UnitMapper mapper;

    public UnitService(UnitMapper mapper) {
        this.mapper = mapper;
    }

    public UnitDto getUnitById(Long id) {
        return mapper.findById(id);
    }

    public List<UnitDto> getUnitList() {
        return mapper.findAll();
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

    public void createUnit(Unit u) {
        mapper.insert(u);
    }

    public void updateUnit(Unit u) {
        mapper.update(u);
    }

    public void deleteUnit(Long id) {
        mapper.delete(id);
    }

    public List<UnitDto> getFullUnitList() {
        return mapper.findAllFull();
    }

    public PagedDtoList<UnitDto> getPagedUnits(Integer page, Integer size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<UnitDto> list = mapper.getPagedUnits(page * size, size, sortColumn, sortDirection);
        return toPagedDto(list, page, size);
    }

    public Integer getCount() {
        return mapper.getCount();
    }

    public PagedDtoList<UnitDto> search(String query, Integer page, Integer size, String sort) {
        return search(query, query, query, query, query, page, size, sort);
    }

    public PagedDtoList<UnitDto> search(
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

        return toPagedDto(list, page, size);
    }

    private PagedDtoList<UnitDto> toPagedDto(List<UnitDto> list, int page, int size) {
        PagedDtoList<UnitDto> pagedList = new PagedDtoList<>();
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
