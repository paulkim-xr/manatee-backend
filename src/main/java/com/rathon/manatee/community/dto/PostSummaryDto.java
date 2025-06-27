package com.rathon.manatee.community.dto;

import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostSummaryDto {
    private Long id;
    private EmployeeDto author;
    private Date posted;
    private String title;
    private Boolean isAnnouncement;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
