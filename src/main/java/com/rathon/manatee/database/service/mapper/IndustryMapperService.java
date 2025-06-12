package com.rathon.manatee.database.service.mapper;

import com.rathon.manatee.database.dto.IndustryDto;
import com.rathon.manatee.database.model.Industry;
import org.springframework.stereotype.Component;

@Component
public class IndustryMapperService {
    public IndustryDto toDto(Industry i) {
        return new IndustryDto(i.id(), i.name());
    }

    public Industry toEntity(IndustryDto d) {
        return new Industry(d.id(), d.name());
    }
}
