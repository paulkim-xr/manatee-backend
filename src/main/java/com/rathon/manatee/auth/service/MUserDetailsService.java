package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MUserDetailsService implements UserDetailsService {
    private final EmployeeMapper mapper;
    private final UserGroupService userGroupService;

    public MUserDetailsService(EmployeeMapper mapper, UserGroupService userGroupService) {
        this.mapper = mapper;
        this.userGroupService = userGroupService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Employee e = mapper.findByUsername(username);
        if (e == null) return User.builder().build();
//        List<UserGroupDto> list = userGroupService.findGroupsByUserId(e.getId());
//        List<UserRoleDto> roles = new ArrayList<>();
//        for (UserGroupDto group : list) {
//            roles.addAll(userGroupService.getRoles(group.getId()));
//        }
//        Map<String, String> buttons = new HashMap<>();
//        for (UserRoleDto role : roles) {
//            for (UserPermissionDto permissionDto : role.getPermissions()) {
//                for (UIComponentDto button : permissionDto.getComponents()) {
//                    buttons.put(button.getName(), button.getDescription());
//                }
//            }
//
//        }

        return User.builder()
                .username(username)
                .password(e.getPasswordHash())
//                .roles(roles.stream().map(UserRoleDto::getName).toList().toArray(new String[0]))
//                .authorities(buttons.keySet().toArray(new String[0]))
                .build();
    }
}
