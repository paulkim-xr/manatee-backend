package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.core.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrivilegeDto extends Dto<UserPrivilege> {
    private String name;
    private UIComponentDto[] components;
}
