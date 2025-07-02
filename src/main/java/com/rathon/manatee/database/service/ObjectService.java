package com.rathon.manatee.database.service;

import com.rathon.manatee.database.dto.ObjectDto;
import com.rathon.manatee.database.dto.PagedList;
import com.rathon.manatee.database.mapper.ObjectMapper;

import java.util.List;

public class ObjectService<T> {
    private final ObjectMapper<T> mapper;

    public ObjectService(ObjectMapper<T> mapper) {
        this.mapper = mapper;
    }

    public ObjectDto<T> getObjectById(Long id) {
        return mapper.findById(id);
    }

    public List<ObjectDto<T>> getObjectList() {
        return mapper.findAll();
    }

    public PagedList<ObjectDto<T>> getPagedObjects(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<ObjectDto<T>> list = mapper.getPagedObjects(page * size, size, sortColumn, sortDirection);
        return toPagedDto(list, page, size);
    }

    public PagedList<ObjectDto<T>> search(
            String query,
            int page,
            int size,
            String sort
    ) {
        return search(query, query, query, query, page, size, sort);
    }

    public PagedList<ObjectDto<T>> search(
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

        List<ObjectDto<T>> list = mapper.search(page * size, size, name, address, industry, registrationNumber, sortColumn, sortDirection);

        return toPagedDto(list, page, size);
    }

    public PagedList<ObjectDto<T>> toPagedDto(List<ObjectDto<T>> list, int page, int size) {
        PagedList<ObjectDto<T>> pagedList = new PagedList<>();
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
