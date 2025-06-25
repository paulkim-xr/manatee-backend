package com.rathon.manatee.community.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardGroup {
    private Long id;
    private String name;
    private Long parentId;
}
