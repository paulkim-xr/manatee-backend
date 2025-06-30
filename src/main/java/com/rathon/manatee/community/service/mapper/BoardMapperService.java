package com.rathon.manatee.community.service.mapper;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.model.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapperService {
    public BoardDto toDto(Board b) {
        BoardDto d = new BoardDto();
        d.setId(b.getId());
        d.setName(b.getName());
        d.setDescription(b.getDescription());
        d.setType(b.getType());

        return d;
    }

    public Board toEntity(BoardDto d) {
        Board b = new Board();
        b.setId(d.getId());
        b.setName(d.getName());
        b.setDescription(d.getDescription());
        b.setType(d.getType());

        return b;
    }
}
