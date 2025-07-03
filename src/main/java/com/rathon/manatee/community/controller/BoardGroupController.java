package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.model.BoardGroup;
import com.rathon.manatee.community.service.BoardGroupService;
import com.rathon.manatee.community.service.BoardService;
import com.rathon.manatee.core.controller.ObjectController;
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

    @Secured("ROLE_ADMIN")
    @Override
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BoardGroupDto d) {
        return super.insert(d);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody BoardGroupDto d) {
        return super.update(d);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }
}
