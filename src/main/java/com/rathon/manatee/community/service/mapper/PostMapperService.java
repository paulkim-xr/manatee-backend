package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapperService implements ObjectMapperService<Post, PostDto> {
    private EmployeeMapper employeeMapper;
    private CommentMapper commentMapper;

    public PostMapperService(EmployeeMapper employeeMapper, CommentMapper commentMapper) {
        this.employeeMapper = employeeMapper;
        this.commentMapper = commentMapper;
    }

    public PostSummaryDto summarize(Post p) {
        PostSummaryDto d = new PostSummaryDto();
        d.setId(p.getId());
        d.setPosted(p.getPostedTime());
        d.setAuthor(employeeMapper.findByIdDto(p.getAuthorId()));
        d.setBoardId(p.getBoardId());
        d.setTitle(p.getTitle());
        d.setIsAnnouncement(p.getIsAnnouncement());
        d.setCommentCount(commentMapper.countByPostId(p.getId()));
        d.setLikeCount(0);
        d.setViewCount(0);

        return d;
    }

    @Override
    public PostDto toDto(Post p) {
        PostDto d = new PostDto();
        d.setId(p.getId());
        d.setPosted(p.getPostedTime());
        d.setAuthor(employeeMapper.findByIdDto(p.getAuthorId()));
        d.setBoardId(p.getBoardId());
        d.setTitle(p.getTitle());
        d.setIsAnnouncement(p.getIsAnnouncement());
        d.setCommentCount(commentMapper.countByPostId(p.getId()));
        d.setLikeCount(0);
        d.setViewCount(0);
        d.setEdited(p.getEditedTime());
        d.setContent(p.getContent());

        return d;
    }

    @Override
    public Post toEntity(PostDto d) {
        Post p = new Post();
        p.setId(d.getId());
        p.setPostedTime(d.getPosted());
        p.setAuthorId(d.getAuthor().getId());
        p.setBoardId(d.getBoardId());
        p.setTitle(d.getTitle());
        p.setIsAnnouncement(d.getIsAnnouncement());
        p.setEditedTime(d.getEdited());
        p.setContent(d.getContent());

        return p;
    }
}
