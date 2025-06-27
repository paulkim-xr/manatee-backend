package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    Board findById(Long id);
    List<Board> findAll();
}
