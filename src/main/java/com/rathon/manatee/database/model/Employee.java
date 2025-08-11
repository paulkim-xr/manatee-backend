package com.rathon.manatee.database.model;

import com.rathon.manatee.core.types.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Employee {
    private Long id;
    private String name;
    private Long unitId;
    private Long positionId;
    private String email;
    private String phone;
    private Date dob;

    private RoleType roleType;

    private String username;
    private String passwordHash;

    private Boolean otpEnabled;
    private Boolean bioEnabled;
    private Boolean passwordless;
}
