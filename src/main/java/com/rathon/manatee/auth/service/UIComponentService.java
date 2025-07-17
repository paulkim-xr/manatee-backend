package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.auth.service.mapper.UIComponentMapperService;
import com.rathon.manatee.auth.service.mapper.UserPermissionMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIComponentService extends ObjectService<UIComponent, UIComponentDto, UIComponentMapper, UIComponentMapperService> {
    private final UserPermissionMapper permissionMapper;
    private final UserPermissionMapperService permissionMapperService;

    public UIComponentService(
            UIComponentMapper mapper,
            UIComponentMapperService service,
            UserPermissionMapper permissionMapper,
            UserPermissionMapperService permissionMapperService
    ) {
        super(mapper, service);
        this.permissionMapper = permissionMapper;
        this.permissionMapperService = permissionMapperService;
    }

    public Boolean checkUnique(String name) {
        return mapper.checkUniqueName(name);
    }

    public List<UserPermissionDto> getPermissions(Long id) {
        return permissionMapper.findPermissionsByComponentId(id).stream().map(permissionMapperService::toDto).toList();
    }

    public void setPermissions(Long id, Long[] ids) {
        mapper.mapPermissions(id, ids);
    }
}
