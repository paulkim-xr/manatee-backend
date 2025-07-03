package com.rathon.manatee.auth.entitySecurity;

import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentSecurity {
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public boolean ownsEntity(Long id, Authentication authentication) {
        Comment p = mapper.findById(id);
        EmployeeDto e = employeeMapper.findByIdDto(p.getAuthorId());
        return Objects.equals(authentication.getName(), e.getUsername());
    }
}
