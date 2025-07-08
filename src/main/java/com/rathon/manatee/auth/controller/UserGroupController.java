package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.auth.service.UserGroupService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/group")
public class UserGroupController extends ObjectController<UserGroup, UserGroupDto, UserGroupService> {
    public UserGroupController(UserGroupService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PagedList<UserGroupDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return super.getPaged(page, size, sort);
    }
}
