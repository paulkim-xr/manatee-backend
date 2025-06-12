package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.model.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionMapper {
    Position findById(Long id);
    List<Position> findAll();
    void insert(Position unitType);
    void update(Position unitType);
    void delete(Long id);
}
