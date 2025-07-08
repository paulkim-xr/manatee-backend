package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper extends ObjectMapper<UserGroup, UserGroupDto> {
}
