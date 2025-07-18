package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.mapper.UnitMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleMapperService implements ObjectMapperService<UserRole, UserRoleDto> {
    private final UserRoleMapper mapper;
    private final EmployeeMapper employeeMapper;
    private final UnitMapper unitMapper;
    private final UserPermissionMapper permissionMapper;
    private final UserPermissionMapperService permissionMapperService;

    public UserRoleMapperService(
            UserRoleMapper mapper,
            EmployeeMapper employeeMapper,
            UnitMapper unitMapper,
            UserPermissionMapper permissionMapper,
            UserPermissionMapperService permissionMapperService
    ) {
        this.mapper = mapper;
        this.employeeMapper = employeeMapper;
        this.unitMapper = unitMapper;
        this.permissionMapper = permissionMapper;
        this.permissionMapperService = permissionMapperService;
    }

    @Override
    public UserRoleDto toDto(UserRole object) {
        UserRoleDto dto = new UserRoleDto();
        dto.setId(object.getId());
        dto.setName(object.getName());
        if (object.getParentId() != null) {
            dto.setParent(new IdNameDto(object.getParentId(), mapper.findById(object.getParentId()).getName()));
        }
        UnitDto unitDto = unitMapper.findByIdDto(object.getManagingUnitId());
        if (unitDto != null) {
            dto.setManagingUnit(new IdNameDto(unitDto.getId(), unitDto.getName()));
        }
        EmployeeDto employeeDto = employeeMapper.findByIdDto(object.getManagerId());
        if (employeeDto != null) {
            dto.setManager(new IdNameDto(object.getManagerId(), employeeDto.getName()));
        }
        dto.setPermissions(collectPermissions(object));
        dto.setChildrenCount(mapper.countChildren(object.getId()));

        return dto;
    }

    private List<UserPermissionDto> collectPermissions(UserRole role) {
        return collectPermissions(role, null).values().stream().toList();
    }

    private Map<Long, UserPermissionDto> collectPermissions(UserRole role, Map<Long, UserPermissionDto> list) {
        if (list == null) {
            return collectPermissions(role, new HashMap<>());
        }

        List<UserPermissionDto> permissions = permissionMapper.findPermissionsByRoleId(role.getId()).stream().map(permissionMapperService::toDto).toList();
        for (UserPermissionDto permission : permissions) {
            list.putIfAbsent(permission.getId(), permission);
        }
        List<UserRole> children = mapper.findByParentId(role.getId());
        if (!children.isEmpty()) {
            for (UserRole child : children) {
                collectPermissions(child, list);
            }
        }

        return list;
    }

    @Override
    public UserRole toEntity(UserRoleDto dto) {
        UserRole object = new UserRole();
        object.setId(dto.getId());
        object.setName(dto.getName());
        object.setParentId(dto.getParent().id());
        if (dto.getManagingUnit() != null) {
            object.setManagingUnitId(dto.getManagingUnit().id());
        }
        if (dto.getManager() != null) {
            object.setManagerId(dto.getManager().id());
        }

        return object;
    }
}
