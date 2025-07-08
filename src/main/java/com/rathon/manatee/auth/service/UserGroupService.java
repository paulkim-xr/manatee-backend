package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.mapper.UserGroupMapper;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.auth.service.mapper.UserGroupMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService extends ObjectService<UserGroup, UserGroupDto, UserGroupMapper, UserGroupMapperService> {
    public UserGroupService(UserGroupMapper mapper, UserGroupMapperService service) {
        super(mapper, service);
    }
}
