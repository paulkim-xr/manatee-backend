package com.rathon.manatee.core.service.mapper;

import com.rathon.manatee.core.dto.ObjectDto;

public abstract class ObjectMapperService<T> {
    public abstract ObjectDto<T> toDto(T object);
    public abstract T toEntity(ObjectDto<T> dto);
}
