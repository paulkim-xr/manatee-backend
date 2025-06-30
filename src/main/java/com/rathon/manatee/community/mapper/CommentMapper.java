package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Comment;

import java.util.List;

public interface CommentMapper {
    Comment findById(Long id);
    List<Comment> findAll();
    List<Comment> findByPostId(Long id);
    List<Comment> findByParentId(Long id);
}
