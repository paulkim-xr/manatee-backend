package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.auth.service.mapper.UIComponentMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

@Service
public class UIComponentService extends ObjectService<UIComponent, UIComponentDto, UIComponentMapper, UIComponentMapperService> {
    public UIComponentService(UIComponentMapper mapper, UIComponentMapperService service) {
        super(mapper, service);
    }

    public Boolean checkUnique(String name) {
        return mapper.checkUniqueName(name);
    }
}
