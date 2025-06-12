package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.PositionDto;
import com.rathon.manatee.database.model.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperService {
    public PositionDto toDto(Position i) {
        return new PositionDto(i.id(), i.name());
    }

    public Position toEntity(PositionDto d) {
        return new Position(d.id(), d.name());
    }
}
