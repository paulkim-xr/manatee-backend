package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UserPrivilegeDto;
import com.rathon.manatee.auth.model.UserPrivilege;
import com.rathon.manatee.auth.service.UserPrivilegeService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/privilege")
public class UserPrivilegeController extends ObjectController<UserPrivilege, UserPrivilegeDto, UserPrivilegeService> {
    public UserPrivilegeController(UserPrivilegeService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PagedList<UserPrivilegeDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return super.getPaged(page, size, sort);
    }
}
