package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper extends ObjectMapper<Board, BoardDto> {
    List<Board> findBoardsByParentId(Long id);
}
