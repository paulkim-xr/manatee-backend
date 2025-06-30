package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BoardGroupMapperService {
    @Autowired
    private BoardMapperService boardMapperService;

    public BoardGroupDto toDto(BoardGroup g) {
        BoardGroupDto d = new BoardGroupDto();
        d.setId(g.getId());
        d.setName(g.getName());

        return d;
    }

    public BoardGroup toEntity(BoardGroupDto d) {
        BoardGroup b = new BoardGroup();
        b.setId(d.getId());
        b.setName(d.getName());
        b.setParentId(0L); // TODO

        return b;
    }

    public BoardGroupDto toDto(BoardGroup g, List<Board> bList, List<BoardGroup> gList) {
        BoardGroupDto d = toDto(g);

        d.setChildBoards(bList.stream().map(boardMapperService::toDto).toList());
        d.setChildGroups(gList.stream().map(this::toDto).toList());

        return d;
    }
}
