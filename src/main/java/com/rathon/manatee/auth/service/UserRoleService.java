package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UserRoleMapper;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.auth.service.mapper.UserRoleMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends ObjectService<UserRole, UserRoleDto, UserRoleMapper, UserRoleMapperService> {
    public UserRoleService(UserRoleMapper mapper, UserRoleMapperService service) {
        super(mapper, service);
    }
}
