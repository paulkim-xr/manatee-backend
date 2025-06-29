package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardEntityDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.mapper.BoardGroupMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGroupService {
    private final BoardGroupMapper mapper;

    public BoardGroupService(BoardGroupMapper mapper) {
        this.mapper = mapper;
    }

    public BoardGroupDto getById(Long id) {
        BoardGroup g = this.mapper.findById(id);

        return toDto(g, mapper.getBoards(id), mapper.getChildGroups(id));
    }

    public List<BoardEntityDto> getChildren(Long id) {
        List<BoardGroup> groups = mapper.getChildGroups(id);
        List<Board> boards = mapper.getBoards(id);

        return null;
    }

    private BoardDto toDto(Board b) {
        BoardDto d = new BoardDto();
        d.setId(b.getId());
        d.setName(b.getName());
        d.setDescription(b.getDescription());
        d.setType(b.getType());
        return d;
    }

    private BoardGroupDto toDto(BoardGroup g) {
        BoardGroupDto d = new BoardGroupDto();
        d.setId(g.getId());
        d.setName(g.getName());

        return d;
    }

    private BoardGroupDto toDto(BoardGroup g, List<Board> bList, List<BoardGroup> gList) {
        BoardGroupDto d = toDto(g);

        d.setChildBoards(bList.stream().map(this::toDto).toList());
        d.setChildGroups(gList.stream().map(this::toDto).toList());

        return d;
    }

    public List<BoardDto> getBoards(Long id) {
        return mapper.getBoards(id).stream().map(this::toDto).toList();
    }

    public List<BoardGroupDto> getChildGroups(Long id) {
        return mapper.getChildGroups(id).stream().map(this::toDto).toList();
    }
}
