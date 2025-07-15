package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface UserRoleMapper extends ObjectMapper<UserRole, UserRoleDto> {
    List<UserRole> findRolesByGroupId(Long id);

    List<UserRole> findRoots();

    List<UserRole> findByParentId(Long id);
    Integer countChildren(Long id);

    List<UserRole> findRolesByPermissionId(Long id);

    void mapGroups(Long id, Long[] ids);

    void mapPermissions(Long id, Long[] ids);
}
