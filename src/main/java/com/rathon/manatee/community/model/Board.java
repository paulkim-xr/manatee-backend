package com.rathon.manatee.community.model;

import com.rathon.manatee.core.types.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    private Long id;
    private String name;
    private Long parentId;
    private String description;
    private BoardType type;

}
