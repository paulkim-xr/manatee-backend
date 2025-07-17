package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.community.service.BoardGroupService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boardgroups")
public class BoardGroupController extends ObjectController<BoardGroup, BoardGroupDto, BoardGroupService> {
    public BoardGroupController(BoardGroupService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PagedList<BoardGroupDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BoardGroupDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGroupDto> getObject(@PathVariable Long id) {
        return super.getObject(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<BoardGroupDto>> search(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @Secured("ROLE_ADMIN")
    @Override
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody BoardGroupDto d) {
        return super.insert(d);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody BoardGroupDto d) {
        return super.update(d);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return super.delete(id);
    }
//----------------------------------------------------------------------------------

    @GetMapping("/root")
    public ResponseEntity<BoardGroupDto> getRoot() {
        return ResponseEntity.ok(service.getObjectById(1L));
    }

    @GetMapping("/{id}/boards")
    public ResponseEntity<List<BoardDto>> getBoards(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBoards(id));
    }

    @GetMapping("/{id}/boardgroups")
    public ResponseEntity<List<BoardGroupDto>> getChildGroups(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildGroups(id));
    }
}
