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
import java.util.List;

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
        return collectComponents(permission, new ArrayList<>());
    }

    private List<UIComponentDto> collectComponents(UserPermission permission, List<UIComponentDto> list) {
        if (list == null) {
            return collectComponents(permission, new ArrayList<>());
        }

        // TODO - remove redundant components
        list.addAll(componentMapper.findByPermissionId(permission.getId()).stream().map(componentMapperService::toDto).toList());
        if (permission.getParentId() != null) {
            return collectComponents(mapper.findById(permission.getParentId()), list);
        }

        return list;
    }

    @Override
    public UserPermission toEntity(UserPermissionDto dto) {
        UserPermission object = new UserPermission();
        object.setId(dto.getId());
        object.setName(dto.getName());
        object.setParentId(dto.getParent().id());

        return object;
    }
}
