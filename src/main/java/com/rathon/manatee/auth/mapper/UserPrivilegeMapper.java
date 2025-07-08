package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPrivilegeMapper extends ObjectMapper<UserPrivilege, UserPrivilegeDto> {
}
