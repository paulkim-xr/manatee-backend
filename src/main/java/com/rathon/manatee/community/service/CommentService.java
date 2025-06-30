package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentMapper mapper;

    public CommentService(CommentMapper mapper) {
        this.mapper = mapper;
    }

    public CommentDto getComment(Long id) {
        return toDto(mapper.findById(id));
    }

    public static CommentDto toDto(Comment c) {
       CommentDto d = new CommentDto();

       return d;
    }
}
