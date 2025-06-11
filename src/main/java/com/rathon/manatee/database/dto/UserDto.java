package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private Boolean active;
    private Date lastActive;
}
