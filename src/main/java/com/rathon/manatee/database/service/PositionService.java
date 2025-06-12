package com.rathon.manatee.database.service;

import com.rathon.manatee.database.mapper.PositionMapper;
import com.rathon.manatee.database.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    private final PositionMapper mapper;

    public PositionService(PositionMapper mapper) {
        this.mapper = mapper;
    }

    public Position getPositionById(Long id) {
        return this.mapper.findById(id);
    }

    public List<Position> getAll() {
        return this.mapper.findAll();
    }

    public void createPosition(Position u) {
        mapper.insert(u);
    }

    public void updatePosition(Position u) {
        mapper.update(u);
    }

    public void deletePosition(Long id) {
        mapper.delete(id);
    }
}
