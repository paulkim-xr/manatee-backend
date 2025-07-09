package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UserGroupMapperService implements ObjectMapperService<UserGroup, UserGroupDto> {
    private final UserRoleMapper roleMapper;
    private final UserRoleMapperService roleMapperService;

    public UserGroupMapperService(UserRoleMapper roleMapper, UserRoleMapperService roleMapperService) {
        this.roleMapper = roleMapper;
        this.roleMapperService = roleMapperService;
    }

    @Override
    public UserGroupDto toDto(UserGroup object) {
        UserGroupDto dto = new UserGroupDto();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setRoles(roleMapper.findRolesByGroupId(object.getId()).stream().map(roleMapperService::toDto).toList());

        return dto;
    }

    @Override
    public UserGroup toEntity(UserGroupDto dto) {
        UserGroup object = new UserGroup();
        object.setId(dto.getId());
        object.setName(dto.getName());

        return object;
    }
}
