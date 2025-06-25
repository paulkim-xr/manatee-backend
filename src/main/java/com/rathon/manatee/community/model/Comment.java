package com.rathon.manatee.community.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Comment {
    private Long id;
    private Long parentId;
    private Long authorId;
    private Date postedTime;
    private Date editedTime;
    private String content;
//    private Integer likeCount;
}
