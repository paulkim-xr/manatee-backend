package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.mapper.UserPermissionMapper;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.auth.service.mapper.UserPermissionMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService extends ObjectService<UserPermission, UserPermissionDto, UserPermissionMapper, UserPermissionMapperService> {
    public UserPermissionService(UserPermissionMapper mapper, UserPermissionMapperService service) {
        super(mapper, service);
    }


    public List<UserPermissionDto> getRoots() {
        return mapper.findRoots().stream().map(service::toDto).toList();
    }

    public List<UserPermissionDto> getChildren(Long id) {
        return mapper.findByParentId(id).stream().map(service::toDto).toList();
    }
}
