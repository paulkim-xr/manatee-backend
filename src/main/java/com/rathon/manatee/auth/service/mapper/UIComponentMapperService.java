package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UIComponentMapperService implements ObjectMapperService<UIComponent, UIComponentDto> {
    @Override
    public UIComponentDto toDto(UIComponent u) {
        UIComponentDto d = new UIComponentDto();
        d.setId(u.getId());

        return d;
    }

    @Override
    public UIComponent toEntity(UIComponentDto dto) {
        return null;
    }
}
