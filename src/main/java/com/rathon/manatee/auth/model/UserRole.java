package com.rathon.manatee.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRole {
    private Long id;
    private String name;
    private Long parentId;
    private Long managingUnitId;
    private Long managerId;
}
