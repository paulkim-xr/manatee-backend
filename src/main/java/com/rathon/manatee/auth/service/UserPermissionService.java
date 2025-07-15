package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.auth.service.mapper.UIComponentMapperService;
import com.rathon.manatee.auth.service.mapper.UserPermissionMapperService;
import com.rathon.manatee.auth.service.mapper.UserRoleMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService extends ObjectService<UserPermission, UserPermissionDto, UserPermissionMapper, UserPermissionMapperService> {

    private final UserRoleMapper userRoleMapper;
    private final UserRoleMapperService userRoleMapperService;
    private final UIComponentMapper componentMapper;
    private final UIComponentMapperService componentMapperService;

    public UserPermissionService(
            UserPermissionMapper mapper,
            UserPermissionMapperService service,
            UserRoleMapper userRoleMapper,
            UserRoleMapperService userRoleMapperService,
            UIComponentMapper componentMapper,
            UIComponentMapperService componentMapperService
    ) {
        super(mapper, service);
        this.userRoleMapper = userRoleMapper;
        this.userRoleMapperService = userRoleMapperService;
        this.componentMapper = componentMapper;
        this.componentMapperService = componentMapperService;
    }


    public List<UserPermissionDto> getRoots() {
        return mapper.findRoots().stream().map(service::toDto).toList();
    }

    public List<UserPermissionDto> getChildren(Long id) {
        return mapper.findByParentId(id).stream().map(service::toDto).toList();
    }

    public List<UserRoleDto> findRoles(Long id) {
        return userRoleMapper.findRolesByPermissionId(id).stream().map(userRoleMapperService::toDto).toList();
    }

    public List<UIComponentDto> findComponents(Long id) {
        return componentMapper.findByPermissionId(id).stream().map(componentMapperService::toDto).toList();
    }
}
