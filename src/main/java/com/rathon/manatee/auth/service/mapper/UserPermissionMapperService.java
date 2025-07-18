package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPermissionMapperService implements ObjectMapperService<UserPermission, UserPermissionDto> {
    private final UserPermissionMapper mapper;
    private final UIComponentMapper componentMapper;
    private final UIComponentMapperService componentMapperService;


    public UserPermissionMapperService(
            UserPermissionMapper mapper,
            UIComponentMapper componentMapper,
            UIComponentMapperService componentMapperService
    ) {
        this.mapper = mapper;
        this.componentMapper = componentMapper;
        this.componentMapperService = componentMapperService;
    }

    @Override
    public UserPermissionDto toDto(UserPermission object) {
        UserPermissionDto dto = new UserPermissionDto();
        dto.setId(object.getId());
        dto.setName(object.getName());
        if (object.getParentId() != null) {
            dto.setParent(new IdNameDto(object.getParentId(), mapper.findById(object.getParentId()).getName()));
        }
        dto.setComponents(this.collectComponents(object));
        dto.setChildrenCount(mapper.countChildren(object.getId()));

        return dto;
    }

    private List<UIComponentDto> collectComponents(UserPermission permission) {
        return collectComponents(permission, null).values().stream().toList();
    }

    private Map<Long, UIComponentDto> collectComponents(UserPermission permission, Map<Long, UIComponentDto> list) {
        if (list == null) {
            return collectComponents(permission, new HashMap<>());
        }

        List<UIComponentDto> components = componentMapper.findByPermissionId(permission.getId()).stream().map(componentMapperService::toDto).toList();
        for (UIComponentDto component : components) {
            list.putIfAbsent(component.getId(), component);
        }
        List<UserPermission> children = mapper.findByParentId(permission.getId());
        if (!children.isEmpty()) {
            for (UserPermission child : children) {
                collectComponents(child, list);
            }
        }

        return list;
    }

    @Override
    public UserPermission toEntity(UserPermissionDto dto) {
        UserPermission object = new UserPermission();
        object.setId(dto.getId());
        object.setName(dto.getName());
        if (dto.getParent() != null) {
            object.setParentId(dto.getParent().id());
        }

        return object;
    }
}
