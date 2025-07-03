package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends ObjectMapper<Comment, CommentDto> {
    List<Comment> findByPostId(Long id);
    List<Comment> findByParentId(Long id);

    Integer countByPostId(Long id);
}
