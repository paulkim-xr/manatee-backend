package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public CommentDto toDto(Comment c) {
        CommentDto d = new CommentDto();
        d.setId(c.getId());
        d.setPostId(c.getPostId());
        d.setParentId(c.getParentId());
        d.setAuthor(employeeMapper.findById(c.getAuthorId()));
        d.setPostedTime(c.getPostedTime());
        d.setEditedTime(c.getEditedTime());
        d.setContent(c.getContent());

        return d;
    }

    public Comment toEntity(CommentDto d) {
        Comment c = new Comment();
        c.setId(d.getId());
        c.setPostId(d.getPostId());
        c.setParentId(d.getParentId());
        c.setAuthorId(d.getAuthor().getId());
        c.setPostedTime(d.getPostedTime());
        c.setEditedTime(d.getEditedTime());
        c.setContent(d.getContent());

        return c;
    }
}
