package com.rathon.manatee.core.service;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.PagedDtoList;
import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;

import java.util.List;

public class ObjectService<T> {
    private final ObjectMapper<T> mapper;
    private final ObjectMapperService<T> service;

    public ObjectService(ObjectMapper<T> mapper, ObjectMapperService<T> service) {
        this.mapper = mapper;
        this.service = service;
    }

    public PagedDtoList<Dto<T>> toPagedDto(List<Dto<T>> list, int page, int size) {
        PagedDtoList<Dto<T>> pagedList = new PagedDtoList<>();
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

    public Dto<T> getObjectById(Long id) {
        return service.toDto(mapper.findById(id));
    }

    public List<Dto<T>> getAll() {
        return mapper.findAll().stream().map(service::toDto).toList();
    }

    public PagedDtoList<Dto<T>> getPagedObjects(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<Dto<T>> list = mapper.getPagedObjects(page * size, size, sortColumn, sortDirection).stream().map(service::toDto).toList();
        return toPagedDto(list, page, size);
    }

    public PagedDtoList<Dto<T>> search(
            int page,
            int size,
            String sort,
            String query
    ) {

        return search(page, size, sort, query, null);
    }

    public PagedDtoList<Dto<T>> search(
            int page,
            int size,
            String sort,
            String... args
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<Dto<T>> list = mapper.search(page * size, size, sortInfo.column, sortInfo.direction, args).stream().map(service::toDto).toList();

        return toPagedDto(list, page, size);
    }

    public void insert(Dto<T> dto) {
        mapper.insert(service.toEntity(dto));
    }

    public void update(Dto<T> dto) {
        mapper.update(service.toEntity(dto));
    }

    public void delete(Long id) {
        mapper.delete(id);
    }
}
