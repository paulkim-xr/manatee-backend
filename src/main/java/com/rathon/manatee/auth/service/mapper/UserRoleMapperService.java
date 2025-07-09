package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UserPrivilegeMapper;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.mapper.UnitMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleMapperService implements ObjectMapperService<UserRole, UserRoleDto> {
    private final UserRoleMapper mapper;
    private final EmployeeMapper employeeMapper;
    private final UnitMapper unitMapper;
    private final UserPrivilegeMapper privilegeMapper;
    private final UserPrivilegeMapperService privilegeMapperService;

    public UserRoleMapperService(
            UserRoleMapper mapper,
            EmployeeMapper employeeMapper,
            UnitMapper unitMapper,
            UserPrivilegeMapper privilegeMapper,
            UserPrivilegeMapperService privilegeMapperService
    ) {
        this.mapper = mapper;
        this.employeeMapper = employeeMapper;
        this.unitMapper = unitMapper;
        this.privilegeMapper = privilegeMapper;
        this.privilegeMapperService = privilegeMapperService;
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
        dto.setManagingUnit(new IdNameDto(unitDto.getId(), unitDto.getName()));
        EmployeeDto employeeDto = employeeMapper.findByIdDto(object.getManagerId());
        dto.setManager(new IdNameDto(object.getManagerId(), employeeDto.getName()));
        dto.setPrivileges(collectPrivileges(object));

        return dto;
    }

    private List<UserPrivilegeDto> collectPrivileges(UserRole role) {
        return collectPrivileges(role, new ArrayList<>());
    }

    private List<UserPrivilegeDto> collectPrivileges(UserRole role, List<UserPrivilegeDto> list) {
        if (list == null) {
            return collectPrivileges(role, new ArrayList<>());
        }
        // TODO - remove redundant privileges
        list.addAll(privilegeMapper.findPrivilegesByRoleId(role.getId()).stream().map(privilegeMapperService::toDto).toList());
        if (role.getParentId() != null) {
            return collectPrivileges(mapper.findById(role.getParentId()), list);
        }

        return list;
    }

    @Override
    public UserRole toEntity(UserRoleDto dto) {
        UserRole object = new UserRole();
        object.setId(dto.getId());
        object.setName(dto.getName());
        object.setParentId(dto.getParent().id());
        object.setManagingUnitId(dto.getManagingUnit().id());
        object.setManagerId(dto.getManager().id());

        return object;
    }
}
