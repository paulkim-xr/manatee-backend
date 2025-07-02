package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.mapper.BoardGroupMapper;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.community.service.mapper.BoardGroupMapperService;
import com.rathon.manatee.community.service.mapper.BoardMapperService;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGroupService extends ObjectService<BoardGroup, BoardGroupDto, BoardGroupMapper, BoardGroupMapperService> {
    private final BoardMapperService boardMapperService;

    public BoardGroupService(BoardGroupMapper mapper, BoardMapperService boardMapperService, BoardGroupMapperService service) {
        super(mapper, service);
        this.boardMapperService = boardMapperService;
    }

    @Override
    public BoardGroupDto getObjectById(Long id) {
        BoardGroup g = this.mapper.findById(id);

        return service.toDto(g, mapper.getBoards(id), mapper.getChildGroups(id));
    }

    public List<BoardDto> getBoards(Long id) {
        return mapper.getBoards(id).stream().map(boardMapperService::toDto).toList();
    }

    public List<BoardGroupDto> getChildGroups(Long id) {
        return mapper.getChildGroups(id).stream().map(service::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        // TODO - delete children as well
        mapper.delete(id);
    }

}
