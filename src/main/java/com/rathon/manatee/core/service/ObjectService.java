package com.rathon.manatee.core.service;

import com.rathon.manatee.core.dto.ObjectDto;
import com.rathon.manatee.core.dto.PagedDtoList;
import com.rathon.manatee.core.mapper.ObjectMapper;

import java.util.List;

public class ObjectService<T> {
    private final ObjectMapper<T> mapper;

    public ObjectService(ObjectMapper<T> mapper) {
        this.mapper = mapper;
    }

    public ObjectDto<T> getObjectById(Long id) {
        return mapper.findByIdDto(id);
    }

    public List<ObjectDto<T>> getObjectList() {
        return mapper.findAllDto();
    }

    public PagedDtoList<ObjectDto<T>> getPagedObjects(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<ObjectDto<T>> list = mapper.getPagedObjectsDto(page * size, size, sortColumn, sortDirection);
        return toPagedDto(list, page, size);
    }

    public PagedDtoList<ObjectDto<T>> search(
            String query,
            int page,
            int size,
            String sort
    ) {

        return search(page, size, sort, query);
    }

    public PagedDtoList<ObjectDto<T>> search(
            int page,
            int size,
            String sort,
            String... args
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<ObjectDto<T>> list = mapper.searchDto(page * size, size, sortInfo.column, sortInfo.direction, args);

        return toPagedDto(list, page, size);
    }

    public PagedDtoList<ObjectDto<T>> toPagedDto(List<ObjectDto<T>> list, int page, int size) {
        PagedDtoList<ObjectDto<T>> pagedList = new PagedDtoList<>();
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

    private static class SortInfo {
        public String column = "id";
        public String direction = "asc";

        public SortInfo(String sort) {
            if (sort != null && sort.contains(",")) {
                column = sort.split(",")[0];
                direction = sort.split(",")[1];
            }
        }
    }
}
