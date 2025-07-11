package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.IdNameDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPermissionDto extends Dto<UserPermission> {
    private String name;
    private IdNameDto parent;
    private List<UIComponentDto> components;
    private Integer childrenCount;
}
