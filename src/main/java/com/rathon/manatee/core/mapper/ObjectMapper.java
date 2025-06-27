package com.rathon.manatee.core.mapper;

import com.rathon.manatee.core.dto.ObjectDto;

import java.util.List;

public interface ObjectMapper<T> {
    T findById(Long id);
    List<T> findAll();
    List<T> getPagedObjects(int offset, int size, String sortColumn, String sortDirection);
    Integer getCount();
    List<T> search(int offset, int size, String sortColumn, String sortDirection, String... args);
    void insert(T object);
    void update(T object);
    void delete(Long id);

    ObjectDto<T> findByIdDto(Long id);
    List<ObjectDto<T>> findAllDto();
    List<ObjectDto<T>> getPagedObjectsDto(int offset, int size, String sortColumn, String sortDirection);
    List<ObjectDto<T>> searchDto(int offset, int size, String sortColumn, String sortDirection, String... args);
}
