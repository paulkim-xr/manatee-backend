package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UserGroupMapperService implements ObjectMapperService<UserGroup, UserGroupDto> {
    @Override
    public UserGroupDto toDto(UserGroup object) {
        return null;
    }

    @Override
    public UserGroup toEntity(UserGroupDto dto) {
        return null;
    }
}
