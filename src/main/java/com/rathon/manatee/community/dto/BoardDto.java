package com.rathon.manatee.community.dto;

import com.rathon.manatee.community.model.BoardType;
import com.rathon.manatee.core.dto.PagedDtoList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto extends BoardEntityDto {
    private String description;
    private BoardType type;
    private PagedDtoList<PostDto> posts;
}
