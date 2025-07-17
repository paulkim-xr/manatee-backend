package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.auth.service.UserGroupService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('rbac_view')")
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
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserGroupDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroupDto> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<UserGroupDto>> search(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('group_add')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody UserGroupDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('group_edit')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserGroupDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('group_delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
//----------------------------------------------------------------------------------

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployees(id));
    }

    @PutMapping("/{id}/employees")
    public ResponseEntity<?> setEmployees(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setEmployees(id, ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<UserRoleDto>> getRoles(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoles(id));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<?> setRoles(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setRoles(id, ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ancestry")
    public ResponseEntity<Long[]> getAncestry(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAncestry(id));
    }
}
