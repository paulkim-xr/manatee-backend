package com.rathon.manatee.core.mapper;

import com.rathon.manatee.core.dto.Dto;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface ObjectMapper<T, D extends Dto<T>> {
    T findById(Long id);
    List<T> findAll();
    List<T> getPagedObjects(int offset, int size, String sortColumn, String sortDirection);
    Integer getCount();

    List<T> searchTemplate(int offset, int size, String sortColumn, String sortDirection, String... args);
    int searchCountTemplate(int offset, int size, String column, String direction, String... args);

    void insert(T object);
    void update(T object);
    void delete(Long id);

    D findByIdDto(Long id);
    List<D> findAllDto();
    List<D> getPagedObjectsDto(int offset, int size, String sortColumn, String sortDirection);
    List<D> searchDto(int offset, int size, String sortColumn, String sortDirection, String... args);
}
