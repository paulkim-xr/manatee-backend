package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleMapperService implements ObjectMapperService<UserRole, UserRoleDto> {
    @Override
    public UserRoleDto toDto(UserRole object) {
        return null;
    }

    @Override
    public UserRole toEntity(UserRoleDto dto) {
        return null;
    }
}
