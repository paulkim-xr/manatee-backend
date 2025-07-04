package com.rathon.manatee.core.service.mapper;

import com.rathon.manatee.core.dto.Dto;

public interface ObjectMapperService<T, D extends Dto<T>> {
    D toDto(T object);
    T toEntity(D dto);
}
