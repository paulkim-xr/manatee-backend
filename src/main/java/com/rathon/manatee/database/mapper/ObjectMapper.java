package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.ObjectDto;

import java.util.List;

public interface ObjectMapper<T> {
    ObjectDto<T> findById(Long id);
    List<ObjectDto<T>> findAll();
    Integer getCount();
    void insert(T object);
    void update(T object);
    void delete(Long id);
    List<ObjectDto<T>> getPaged(int offset, int size);
    List<CompanyDto> search(String[] queries, int offset, int size);
}
