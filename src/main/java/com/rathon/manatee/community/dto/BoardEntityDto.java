package com.rathon.manatee.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEntityDto {
    private Long id;
    private String name;
    private Long parentId;
}
