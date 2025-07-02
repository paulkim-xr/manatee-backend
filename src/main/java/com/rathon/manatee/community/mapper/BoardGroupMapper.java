package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface BoardGroupMapper extends ObjectMapper<BoardGroup> {
    List<BoardGroup> getChildGroups(Long id);
    List<Board> getBoards(Long id);
}
