package com.rathon.manatee.community.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    private Long id;
    private Long groupId;
    private String name;
    private String description;
    private BoardType type;

}
