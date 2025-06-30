package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.mapper.CommentMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentMapper mapper;
    private final CommentMapperService mapperService;

    public CommentService(CommentMapper mapper, CommentMapperService mapperService) {
        this.mapper = mapper;
        this.mapperService = mapperService;
    }

    public Comment getComment(Long id) {
        return mapper.findById(id);
    }

    public void insert(CommentDto comment) {
        mapper.insert(mapperService.toEntity(comment));
    }

    public void update(CommentDto comment) {
        mapper.update(mapperService.toEntity(comment));
    }

    public void delete(Long id) {
        mapper.delete(id);
    }


    public List<CommentDto> getAll() {
        return mapper.findAll().stream().map(mapperService::toDto).toList();
    }
}
