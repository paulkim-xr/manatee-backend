package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface BoardGroupMapper {
    List<BoardGroup> getChildGroups(Long id);
    List<Board> getBoards(Long id);

    BoardGroup findById(Long id);

    void insert(BoardGroup entity);

    void update(BoardGroup entity);

    void delete(Long id);

    List<BoardGroup> findAll();
}
