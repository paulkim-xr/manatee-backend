package com.rathon.manatee.auth.service.mapper;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.mapper.UIComponentMapper;
import com.rathon.manatee.auth.mapper.UserPrivilegeMapper;
import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPrivilegeMapperService implements ObjectMapperService<UserPrivilege, UserPrivilegeDto> {
    private final UserPrivilegeMapper mapper;
    private final UIComponentMapper componentMapper;
    private final UIComponentMapperService componentMapperService;


    public UserPrivilegeMapperService(
            UserPrivilegeMapper mapper,
            UIComponentMapper componentMapper,
            UIComponentMapperService componentMapperService
    ) {
        this.mapper = mapper;
        this.componentMapper = componentMapper;
        this.componentMapperService = componentMapperService;
    }

    @Override
    public UserPrivilegeDto toDto(UserPrivilege object) {
        UserPrivilegeDto dto = new UserPrivilegeDto();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setParent(new IdNameDto(object.getParentId(), mapper.findById(object.getParentId()).getName()));

        return dto;
    }

    private List<UIComponentDto> collectComponents(UserPrivilege privilege) {
        return collectComponents(privilege, new ArrayList<>());
    }

    private List<UIComponentDto> collectComponents(UserPrivilege privilege, List<UIComponentDto> list) {
        if (list == null) {
            return collectComponents(privilege, new ArrayList<>());
        }

        // TODO - remove redundant components
        list.addAll(componentMapper.findByPrivilegeId(privilege.getId()).stream().map(componentMapperService::toDto).toList());
        if (privilege.getParentId() != null) {
            return collectComponents(mapper.findById(privilege.getParentId()), list);
        }

        return list;
    }

    @Override
    public UserPrivilege toEntity(UserPrivilegeDto dto) {
        UserPrivilege object = new UserPrivilege();
        object.setId(dto.getId());
        object.setName(dto.getName());
        object.setParentId(dto.getParent().id());

        return object;
    }
}
