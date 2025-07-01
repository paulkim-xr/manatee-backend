package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardEntityDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.mapper.BoardGroupMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.community.service.mapper.BoardGroupMapperService;
import com.rathon.manatee.community.service.mapper.BoardMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGroupService {
    private final BoardGroupMapper mapper;
    private final BoardMapperService boardMapperService;
    private final BoardGroupMapperService boardGroupMapperService;

    public BoardGroupService(BoardGroupMapper mapper, BoardMapperService boardMapperService, BoardGroupMapperService boardGroupMapperService) {
        this.mapper = mapper;
        this.boardMapperService = boardMapperService;
        this.boardGroupMapperService = boardGroupMapperService;
    }

    public BoardGroupDto getById(Long id) {
        BoardGroup g = this.mapper.findById(id);

        return boardGroupMapperService.toDto(g, mapper.getBoards(id), mapper.getChildGroups(id));
    }

    public List<BoardEntityDto> getChildren(Long id) {
        List<BoardGroup> groups = mapper.getChildGroups(id);
        List<Board> boards = mapper.getBoards(id);

        return null;
    }

    public List<BoardDto> getBoards(Long id) {
        return mapper.getBoards(id).stream().map(boardMapperService::toDto).toList();
    }

    public List<BoardGroupDto> getChildGroups(Long id) {
        return mapper.getChildGroups(id).stream().map(boardGroupMapperService::toDto).toList();
    }

    public void insert(BoardGroupDto d) {
        mapper.insert(boardGroupMapperService.toEntity(d));
    }

    public void update(BoardGroupDto d) {
        mapper.update(boardGroupMapperService.toEntity(d));
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public List<BoardGroupDto> getAll() {
        return mapper.findAll().stream().map(boardGroupMapperService::toDto).toList();
    }
}
