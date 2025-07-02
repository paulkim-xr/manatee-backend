package com.rathon.manatee.community.dto;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardType;
import com.rathon.manatee.core.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto extends Dto<Board> {
    private String name;
    private Long parentId;
    private String description;
    private BoardType type;
}
