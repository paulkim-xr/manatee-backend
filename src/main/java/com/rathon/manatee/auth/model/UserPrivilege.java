package com.rathon.manatee.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrivilege {
    private Long id;
    private String name;
    private Long parentId;
}
