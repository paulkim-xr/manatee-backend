package com.rathon.manatee.community.dto;

import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.core.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardGroupDto extends Dto<BoardGroup> {
    private String name;
    private Long parentId;
    List<BoardDto> childBoards;
    List<BoardGroupDto> childGroups;
}
