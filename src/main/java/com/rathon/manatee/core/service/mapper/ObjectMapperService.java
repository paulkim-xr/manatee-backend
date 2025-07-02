package com.rathon.manatee.core.service.mapper;

import com.rathon.manatee.core.dto.Dto;

public abstract class ObjectMapperService<T> {
    public abstract Dto<T> toDto(T object);
    public abstract T toEntity(Dto<T> dto);
}
