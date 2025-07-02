package com.rathon.manatee.community.dto;

import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostSummaryDto extends Dto<Post> {
    private EmployeeDto author;
    private Date posted;
    private Long boardId;
    private String title;
    private Boolean isAnnouncement;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
