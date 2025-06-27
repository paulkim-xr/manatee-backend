package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.CompanyMapper;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Industry;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyMapper mapper;
    private final IndustryService iService;

    public CompanyService(CompanyMapper mapper, IndustryService iService) {
        this.mapper = mapper;
        this.iService = iService;
    }

    public CompanyDto getCompanyById(Long id) {
        return mapper.findById(id);
    }

    public List<CompanyDto> getCompanyList() {
        return mapper.findAll();
    }

    public List<UnitDto> getUnits(Long id) {
        return mapper.getUnits(id);
    }

    public List<EmployeeDto> getEmployees(Long id) {
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

    public List<UnitDto> getUnitsFull(Long id) {
        return mapper.getUnitsFull();
    }

    public UnitDto getRoot(Long id) {

        return mapper.getRoot(id);
    }

    public PagedDtoList<CompanyDto> search(
            String query,
            int page,
            int size,
            String sort
    ) {
        return search(query, query, query, query, page, size, sort);
    }

    public PagedDtoList<CompanyDto> search(
            String name,
            String address,
            String industry,
            String registrationNumber,
            int page,
            int size,
            String sort
    ) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<CompanyDto> list = mapper.search(name, address, industry, registrationNumber, sortColumn, sortDirection, page * size, size);

        return toPagedDto(list, page, size);
    }

    public PagedDtoList<CompanyDto> getPagedCompanies(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<CompanyDto> list = mapper.getPagedCompanies(page * size, size, sortColumn, sortDirection);
        return toPagedDto(list, page, size);
    }

    public Integer getCount() {
        return mapper.getCount();
    }

    private PagedDtoList<CompanyDto> toPagedDto(List<CompanyDto> list, int page, int size) {
        PagedDtoList<CompanyDto> pagedList = new PagedDtoList<>();
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
