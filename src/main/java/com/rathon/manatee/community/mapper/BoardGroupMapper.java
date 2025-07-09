package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardGroupMapper extends ObjectMapper<BoardGroup, BoardGroupDto> {
    List<BoardGroup> getChildGroups(Long id);
}
