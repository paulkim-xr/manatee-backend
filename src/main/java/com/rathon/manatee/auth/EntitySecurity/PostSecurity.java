package com.rathon.manatee.auth.EntitySecurity;

import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PostSecurity {
    @Autowired
    private PostMapper mapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public boolean ownsEntity(Long id, Authentication authentication) {
        Post p = mapper.findById(id);
        EmployeeDto e = employeeMapper.findByIdDto(p.getAuthorId());
        return Objects.equals(authentication.getName(), e.getUsername());
    }
}
