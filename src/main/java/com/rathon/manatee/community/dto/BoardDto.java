package com.rathon.manatee.community.dto;

import com.rathon.manatee.community.model.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto extends BoardEntityDto {
    private String description;
    private BoardType type;
}
