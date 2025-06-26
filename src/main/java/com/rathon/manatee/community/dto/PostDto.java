package com.rathon.manatee.community.dto;

import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {
    private Long id;
    private EmployeeDto author;
    private Date posted;
    private Date edited;
    private String title;
    private String content;
    private Boolean isAnnouncement;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
