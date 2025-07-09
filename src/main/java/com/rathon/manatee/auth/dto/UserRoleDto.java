package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleDto extends Dto<UserRole> {
    private String name;
    private IdNameDto parent;
    private IdNameDto managingUnit;
    private IdNameDto manager;
    private List<UserPrivilegeDto> privileges;
}
