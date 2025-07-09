package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPrivilegeDto extends Dto<UserPrivilege> {
    private String name;
    private IdNameDto parent;
    private List<UIComponentDto> components;
}
