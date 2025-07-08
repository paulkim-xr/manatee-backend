package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends ObjectMapper<UserRole, UserRoleDto> {
}
