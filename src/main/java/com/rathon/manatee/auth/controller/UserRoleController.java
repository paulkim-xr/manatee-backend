package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.dto.UserPermissionDto;
import com.rathon.manatee.auth.dto.UserRoleDto;
import com.rathon.manatee.auth.model.UserRole;
import com.rathon.manatee.auth.service.UserRoleService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/role")
public class UserRoleController extends ObjectController<UserRole, UserRoleDto, UserRoleService> {
    public UserRoleController(UserRoleService service) {
        super(service);
    }

    //    @PreAuthorize("authentication.getName() == #dto.author.username")
    @GetMapping
    public ResponseEntity<PagedList<UserRoleDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDto> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

    // TODO
    @GetMapping("/search")
    public ResponseEntity<PagedList<UserRoleDto>> search(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserRoleDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserRoleDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roots")
    public ResponseEntity<List<UserRoleDto>> getRoots() {
        return ResponseEntity.ok(service.getRoots());
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<UserRoleDto>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildren(id));
    }
//    ------------------------------------------------------------------------

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<UserGroupDto>> getGroups(@PathVariable Long id) {
        return ResponseEntity.ok(service.findGroups(id));
    }

    @PutMapping("/{id}/groups")
    public ResponseEntity<?> setGroups(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setGroups(id, ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<UserPermissionDto>> getPermissions(@PathVariable Long id) {
        return ResponseEntity.ok(service.findPermissions(id));
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<?> setPermissions(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setPermissions(id, ids);
        return ResponseEntity.ok().build();
    }
}
