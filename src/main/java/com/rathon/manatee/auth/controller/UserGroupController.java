package com.rathon.manatee.auth.controller;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.model.UserGroup;
import com.rathon.manatee.auth.service.UserGroupService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody UserGroupDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserGroupDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
//----------------------------------------------------------------------------------

//    @GetMapping("/roots")
//    public ResponseEntity<List<UserGroupDto>> getRoots() {
//        return ResponseEntity.ok(service.getRoots());
//    }
//
//    @GetMapping("/{id}/children")
//    public ResponseEntity<List<UserGroupDto>> getChildren(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getChildren(id));
//    }
}
