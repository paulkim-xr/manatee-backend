package com.rathon.manatee.database.mapper;

import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.database.dto.CompanyDto;
import org.apache.ibatis.annotations.Mapper;

import com.rathon.manatee.database.model.Company;

import java.util.List;

@Mapper
public interface CompanyMapper extends ObjectMapper<Company, CompanyDto> {
    List<CompanyDto> searchDto(
            String name,
            String address,
            String industry,
            String registrationNumber,
            String sortColumn,
            String sortDirection,
            int offset, int size
    );
    Integer searchCountDto(
            String name,
            String address,
            String industry,
            String registrationNumber
    );
}
