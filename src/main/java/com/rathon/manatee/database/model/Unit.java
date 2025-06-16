package com.rathon.manatee.database.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unit {
    private Long id;
    private Long companyId;
    private String name;
    private Long typeId;
    private String code;
    private Long parentId;
    private Long leaderId;
}
