package com.rathon.manatee.community.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Post {
    private Long id;
    private Long boardId;
    private Long authorId;
    private Date postedTime;
    private Date editedTime;
    private String title;
    private String content;
    private Boolean isAnnouncement;
//    private Integer viewCount;
//    private Integer likeCount;
}
