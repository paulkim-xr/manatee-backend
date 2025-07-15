package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UserGroupMapper;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.auth.service.mapper.UserGroupMapperService;
import com.rathon.manatee.auth.service.mapper.UserPermissionMapperService;
import com.rathon.manatee.auth.service.mapper.UserRoleMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService extends ObjectService<UserRole, UserRoleDto, UserRoleMapper, UserRoleMapperService> {
    private final UserGroupMapper userGroupMapper;
    private final UserGroupMapperService userGroupMapperService;
    private final UserPermissionMapper userPermissionMapper;
    private final UserPermissionMapperService userPermissionMapperService;

    public UserRoleService(
            UserRoleMapper mapper,
            UserRoleMapperService service,
            UserGroupMapper userGroupMapper,
            UserGroupMapperService userGroupMapperService,
            UserPermissionMapper userPermissionMapper,
            UserPermissionMapperService userPermissionMapperService
    ) {
        super(mapper, service);
        this.userGroupMapper = userGroupMapper;
        this.userGroupMapperService = userGroupMapperService;
        this.userPermissionMapper = userPermissionMapper;
        this.userPermissionMapperService = userPermissionMapperService;
    }

    public List<UserRoleDto> findGroupRoles(Long id) {
        return mapper.findRolesByGroupId(id).stream().map(service::toDto).toList();
    }

    public List<UserRoleDto> getRoots() {
        return mapper.findRoots().stream().map(service::toDto).toList();
    }

    public List<UserRoleDto> getChildren(Long id) {
        return mapper.findByParentId(id).stream().map(service::toDto).toList();
    }

    public List<UserGroupDto> findGroups(Long id) {
        return userGroupMapper.findByRoleId(id).stream().map(userGroupMapperService::toDto).toList();
    }

    public List<UserPermissionDto> findPermissions(Long id) {
        return userPermissionMapper.findPermissionsByRoleId(id).stream().map(userPermissionMapperService::toDto).toList();
    }

    public void setGroups(Long id, Long[] ids) {
        mapper.mapGroups(id, ids);
    }

    public void setPermissions(Long id, Long[] ids) {
        mapper.mapPermissions(id, ids);
    }
}
