package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.mapper.BoardGroupMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class BoardGroupMapperService implements ObjectMapperService<BoardGroup, BoardGroupDto> {
    @Autowired
    private BoardMapperService boardMapperService;

    @Override
    public BoardGroupDto toDto(BoardGroup g) {
        BoardGroupDto d = new BoardGroupDto();
        d.setId(g.getId());
        d.setName(g.getName());
        d.setParentId(g.getParentId());

        return d;
    }

    @Override
    public BoardGroup toEntity(BoardGroupDto d) {
        BoardGroup b = new BoardGroup();
        b.setId(d.getId());
        b.setName(d.getName());
        b.setParentId(d.getParentId());

        return b;
    }

    public BoardGroupDto toDto(BoardGroup g, List<Board> bList, List<BoardGroup> gList) {
        BoardGroupDto d = toDto(g);

        d.setChildBoards(bList.stream().map(boardMapperService::toDto).toList());
        d.setChildGroups(gList.stream().map(this::toDto).toList());

        return d;
    }
}
