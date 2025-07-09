package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.mapper.UserGroupMapper;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MUserDetailsService implements UserDetailsService {
    private final EmployeeMapper mapper;
    private final UserGroupService userGroupService;
    private final UserRoleService userRoleService;

    public MUserDetailsService(EmployeeMapper mapper, UserGroupService userGroupService, UserRoleService userRoleService) {
        this.mapper = mapper;
        this.userGroupService = userGroupService;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Employee e = mapper.findByUsername(username);
        if (e == null) return User.builder().build();
        List<UserGroupDto> list = userGroupService.findGroupsByUserId(e.getId());
        List<UserRoleDto> roles = new ArrayList<>();
        for (UserGroupDto group : list) {
            roles.addAll(userRoleService.findGroupRoles(group.getId()));
        }

        return User.builder()
                .username(username)
                .password(e.getPasswordHash())
                .roles(e.getRoleType().name())
//                .roles(roles.stream().map(UserRoleDto::getName).toList().toArray(new String[0]))
                .build();
    }
}
