package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

@Service
public class UserPrivilegeMapperService implements ObjectMapperService<UserPrivilege, UserPrivilegeDto> {
    @Override
    public UserPrivilegeDto toDto(UserPrivilege object) {
        return null;
    }

    @Override
    public UserPrivilege toEntity(UserPrivilegeDto dto) {
        return null;
    }
}
