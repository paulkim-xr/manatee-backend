package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UIComponentDto;
import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserPermission;
import com.rathon.manatee.auth.service.UserPermissionService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/permission")
public class UserPermissionController extends ObjectController<UserPermission, UserPermissionDto, UserPermissionService> {
    public UserPermissionController(UserPermissionService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PagedList<UserPermissionDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserPermissionDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPermissionDto> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<UserPermissionDto>> search(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody UserPermissionDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserPermissionDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roots")
    public ResponseEntity<List<UserPermissionDto>> getRoots() {
        return ResponseEntity.ok(service.getRoots());
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<UserPermissionDto>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildren(id));
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<UserRoleDto>> getRoles(@PathVariable Long id) {
        return ResponseEntity.ok(service.findRoles(id));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<?> setRoles(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setRoles(id, ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/components")
    public ResponseEntity<List<UIComponentDto>> getComponents(@PathVariable Long id) {
        return ResponseEntity.ok(service.findComponents(id));
    }

    @PutMapping("/{id}/components")
    public ResponseEntity<?> setComponents(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setComponents(id, ids);
        return ResponseEntity.ok().build();
    }
}
