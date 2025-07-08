package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.mapper.UserPrivilegeMapper;
import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.auth.service.mapper.UserPrivilegeMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

@Service
public class UserPrivilegeService extends ObjectService<UserPrivilege, UserPrivilegeDto, UserPrivilegeMapper, UserPrivilegeMapperService> {
    public UserPrivilegeService(UserPrivilegeMapper mapper, UserPrivilegeMapperService service) {
        super(mapper, service);
    }
}
