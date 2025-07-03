package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.stereotype.Component;

@Component
public class BoardMapperService implements ObjectMapperService<Board, BoardDto> {
    @Override
    public BoardDto toDto(Board b) {
        BoardDto d = new BoardDto();
        d.setId(b.getId());
        d.setName(b.getName());
        d.setParentId(b.getParentId());
        d.setDescription(b.getDescription());
        d.setType(b.getType());

        return d;
    }

    @Override
    public Board toEntity(BoardDto d) {
        Board b = new Board();
        b.setId(d.getId());
        b.setParentId(d.getParentId());
        b.setName(d.getName());
        b.setDescription(d.getDescription());
        b.setType(d.getType());

        return b;
    }
}
