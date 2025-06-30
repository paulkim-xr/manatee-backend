package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    Comment findById(Long id);
    List<Comment> findAll();
    List<Comment> findByPostId(Long id);
    List<Comment> findByParentId(Long id);

    Integer countByPostId(Long id);

    void insert(Comment comment);
    void update(Comment comment);
    void delete(Long id);
}
