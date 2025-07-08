package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDto extends Dto<UserRole> {
    private String name;
    private Unit managingUnit;
    private Employee manager;
    private UserRoleDto parent;
    private UserPrivilege[] privileges;
}
