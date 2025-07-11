package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.mapper.UserGroupMapper;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.auth.service.mapper.UIComponentMapperService;
import com.rathon.manatee.auth.service.mapper.UserGroupMapperService;
import com.rathon.manatee.auth.service.mapper.UserPermissionMapperService;
import com.rathon.manatee.auth.service.mapper.UserRoleMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService extends ObjectService<UserGroup, UserGroupDto, UserGroupMapper, UserGroupMapperService> {
    private final UserRoleMapper roleMapper;
    private final UserRoleMapperService roleMapperService;
    private final UserPermissionMapper permissionMapper;
    private final UserPermissionMapperService permissionMapperService;
    private final UIComponentMapper componentMapper;
    private final UIComponentMapperService componentMapperService;

    public UserGroupService(
            UserGroupMapper mapper,
            UserGroupMapperService service,
            UserRoleMapper roleMapper,
            UserRoleMapperService roleMapperService,
            UserPermissionMapper permissionMapper,
            UserPermissionMapperService permissionMapperService,
            UIComponentMapper componentMapper,
            UIComponentMapperService componentMapperService
    ) {
        super(mapper, service);
        this.roleMapper = roleMapper;
        this.roleMapperService = roleMapperService;
        this.permissionMapper = permissionMapper;
        this.permissionMapperService = permissionMapperService;
        this.componentMapper = componentMapper;
        this.componentMapperService = componentMapperService;
    }

    public List<UserGroupDto> findGroupsByUserId(Long id) {
        return mapper.findByUserId(id).stream().map(service::toDto).toList();
    }

    public List<UserRoleDto> getRoles(Long id) {
        return roleMapper.findRolesByGroupId(id).stream().map(roleMapperService::toDto).toList();
    }

//    public List<UserGroupDto> getRoots() {
//        return mapper.findRootGroups().stream().map(service::toDto).toList();
//    }
//
//    public List<UserGroupDto> getChildren(Long id) {
//        return mapper.findChildren(id).stream().map(service::toDto).toList();
//    }
}
