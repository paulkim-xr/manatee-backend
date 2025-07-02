package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.mapper.CommentMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService extends ObjectService<Comment, CommentDto, CommentMapper, CommentMapperService> {

    public CommentService(CommentMapper mapper, CommentMapperService service) {
        super(mapper, service);
    }

    public boolean hasChildren(Long id) {
        return mapper.findByParentId(id).isEmpty();
    }

    @Override
    public void delete(Long id) {
        if (mapper.findByParentId(id).isEmpty()) {
            mapper.delete(id);
        } else {
            CommentDto dto = getObjectById(id);
            dto.setContent("삭제된 댓글입니다");
            dto.setEditedTime(new Date());

            mapper.update(service.toEntity(dto));
        }
    }
}
