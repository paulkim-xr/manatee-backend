package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.BoardGroupDto;
import com.rathon.manatee.community.service.BoardGroupService;
import com.rathon.manatee.community.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boardgroups")
public class BoardGroupController {
    private final BoardGroupService gService;
    private final BoardService bService;

    public BoardGroupController(BoardGroupService gService, BoardService bService) {
        this.gService = gService;
        this.bService = bService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BoardGroupDto>> getAll() {
        return ResponseEntity.ok(gService.getAll());
    }

    @GetMapping("/root")
    public ResponseEntity<BoardGroupDto> getRoot() {
        return ResponseEntity.ok(gService.getObjectById(1L));
    }

    @GetMapping("/{id}/boards")
    public ResponseEntity<List<BoardDto>> getBoards(@PathVariable Long id) {
        return ResponseEntity.ok(gService.getBoards(id));
    }

    @GetMapping("/{id}/boardgroups")
    public ResponseEntity<List<BoardGroupDto>> getChildGroups(@PathVariable Long id) {
        return ResponseEntity.ok(gService.getChildGroups(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BoardGroupDto d) {
        gService.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody BoardGroupDto d) {
        gService.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gService.delete(id);
        return ResponseEntity.ok().build();
    }
}
