package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UIComponentMapperService implements ObjectMapperService<UIComponent, UIComponentDto> {
    @Override
    public UIComponentDto toDto(UIComponent object) {
        UIComponentDto dto = new UIComponentDto();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setDescription(object.getDescription());

        return dto;
    }

    @Override
    public UIComponent toEntity(UIComponentDto dto) {
        UIComponent object = new UIComponent();
        object.setId(dto.getId());
        object.setName(dto.getName());
        object.setDescription(dto.getDescription());

        return object;
    }
}
