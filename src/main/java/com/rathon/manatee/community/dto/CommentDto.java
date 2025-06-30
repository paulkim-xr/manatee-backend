package com.rathon.manatee.community.dto;

import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long postId;
    private Long parentId;
    private EmployeeDto author;
    private Date postedTime;
    private Date editedTime;
    private String content;
}
