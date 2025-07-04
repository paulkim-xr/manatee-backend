package com.rathon.manatee.auth;

import com.rathon.manatee.database.mapper.EmployeeMapper;
import com.rathon.manatee.database.model.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MUserDetailsService implements UserDetailsService {
    private final EmployeeMapper mapper;

    public MUserDetailsService(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Employee e = mapper.findByUsername(username);
        if (e == null) return null;

        return User.builder()
                .username(username)
                .password(e.getPasswordHash())
                .roles(e.getRoleType().toString())
                .build();
    }
}
