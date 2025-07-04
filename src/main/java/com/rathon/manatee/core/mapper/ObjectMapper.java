package com.rathon.manatee.core.mapper;

import com.rathon.manatee.core.dto.Dto;

import java.util.List;

public interface ObjectMapper<T, D extends Dto<T>> {
    T findById(Long id);
    List<T> findAll();
    List<T> getPagedObjects(int offset, int size, String sortColumn, String sortDirection);
    Integer getCount();

    List<T> search(int offset, int size, String sortColumn, String sortDirection, String... args);
    int searchCount(String[] args);

    void insert(T object);
    void update(T object);
    void delete(Long id);

    D findByIdDto(Long id);
    List<D> findAllDto();
    List<D> getPagedObjectsDto(int offset, int size, String sortColumn, String sortDirection);
    List<D> searchDto(int offset, int size, String sortColumn, String sortDirection, String... args);
    int searchCountDto(String[] args);
}
