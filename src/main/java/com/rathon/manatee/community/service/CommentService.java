package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.mapper.CommentMapperService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private final CommentMapper mapper;
    private final CommentMapperService mapperService;

    public CommentService(CommentMapper mapper, CommentMapperService mapperService) {
        this.mapper = mapper;
        this.mapperService = mapperService;
    }

    public CommentDto getComment(Long id) {
        return mapperService.toDto(mapper.findById(id));
    }

    public boolean hasChildren(Long id) {
        return mapper.findByParentId(id).isEmpty();
    }

    public void insert(CommentDto comment) {
        mapper.insert(mapperService.toEntity(comment));
    }

    public void update(CommentDto comment) {
        mapper.update(mapperService.toEntity(comment));
    }

    public void delete(Long id) {
        if (mapper.findByParentId(id).isEmpty()) {
            mapper.delete(id);
        } else {
            CommentDto dto = getComment(id);
            dto.setContent("삭제된 댓글입니다");
            dto.setEditedTime(new Date());

            mapper.update(mapperService.toEntity(dto));
        }
    }


    public List<CommentDto> getAll() {
        return mapper.findAll().stream().map(mapperService::toDto).toList();
    }
}
