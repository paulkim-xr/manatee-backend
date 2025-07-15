package com.rathon.manatee.auth.mapper;

import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface UserPermissionMapper extends ObjectMapper<UserPermission, UserPermissionDto> {
    List<UserPermission> findPermissionsByRoleId(Long id);

    List<UserPermission> findRoots();

    List<UserPermission> findByParentId(Long id);

    Integer countChildren(Long id);

    List<UserPermission> findPermissionsByComponentId(Long id);

    void mapRoles(Long id, Long[] ids);

    void mapComponents(Long id, Long[] ids);
}
