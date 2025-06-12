package com.rathon.manatee.database.mapper;

import com.rathon.manatee.database.model.Industry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndustryMapper {
    Industry findById(Long id);
    List<Industry> findAll();
    void insert(Industry industry);
    void update(Industry industry);
    void delete(Long id);
}
