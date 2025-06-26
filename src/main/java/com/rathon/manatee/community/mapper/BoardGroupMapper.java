package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardGroupMapper {
    List<BoardGroup> getChildren(Long id);
    List<Board> getBoards(Long id);
}
