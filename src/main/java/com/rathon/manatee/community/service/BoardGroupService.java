package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardEntityDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.mapper.BoardGroupMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.core.dto.PagedDtoList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGroupService {
    private final BoardGroupMapper mapper;

    public BoardGroupService(BoardGroupMapper mapper) {
        this.mapper = mapper;
    }

    public List<BoardEntityDto> getChildren(Long id) {
        List<BoardGroup> groups = mapper.getChildren(id);
        List<Board> boards = mapper.getBoards(id);

        List<BoardEntityDto> list = new java.util.ArrayList<>(groups.stream().map(this::toDto).toList());
        list.addAll(boards.stream().map(this::toDto).toList());

        return list;
    }

    private BoardEntityDto toDto(Board b) {
        BoardDto d = new BoardDto();
        d.setId(b.getId());
        d.setName(b.getName());
        d.setDescription(b.getDescription());
        d.setType(b.getType());
        d.setPosts(null);

        return d;
    }

    private BoardEntityDto toDto(BoardGroup g) {
        BoardGroupDto d = new BoardGroupDto();
        d.setId(g.getId());
        d.setName(g.getName());

        return d;
    }
}
