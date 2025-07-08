package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.core.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupDto extends Dto<UserGroup> {
    private String name;
    private UserRoleDto[] roles;
}
