package com.rathon.manatee.community.service;

import com.rathon.manatee.community.mapper.BoardMapper;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardMapper mapper;

    public BoardService(BoardMapper mapper) {
        this.mapper = mapper;
    }
}
