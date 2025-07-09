package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface UIComponentMapper extends ObjectMapper<UIComponent, UIComponentDto> {
    List<UIComponent> findByPrivilegeId(Long id);
}
