package com.rathon.manatee.core.service;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;

import java.util.List;

public class ObjectService<T, D extends Dto<T>, M extends ObjectMapper<T>, S extends ObjectMapperService<T, D>> {
    public final M mapper;
    public final S service;

    public ObjectService(M mapper, S service) {
        this.mapper = mapper;
        this.service = service;
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

    public D getObjectById(Long id) {
        return service.toDto(mapper.findById(id));
    }

    public List<D> getAll() {
        return mapper.findAll().stream().map(service::toDto).toList();
    }

    public PagedList<D> getPagedObjects(int page, int size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<D> list = mapper.getPagedObjects(page * size, size, sortColumn, sortDirection).stream().map(service::toDto).toList();
        return PagedList.build(list, page, size, mapper.getCount());
    }

    public PagedList<D> searchTemplate(
            int page,
            int size,
            String sort,
            String query
    ) {

        return searchTemplate(page, size, sort, query, query, query);
    }

    public PagedList<D> searchTemplate(
            int page,
            int size,
            String sort,
            String... args
    ) {
        SortInfo sortInfo = new SortInfo(sort);

        List<D> list = mapper.searchTemplate(page * size, size, sortInfo.column, sortInfo.direction, args).stream().map(service::toDto).toList();
        int totalCount = mapper.searchCountTemplate(page * size, size, sortInfo.column, sortInfo.direction, args);
        return PagedList.build(list, page, size, totalCount);
    }

    public void insert(D dto) {
        mapper.insert(service.toEntity(dto));
    }

    public void update(D dto) {
        mapper.update(service.toEntity(dto));
    }

    public void delete(Long id) {
        mapper.delete(id);
    }
}
