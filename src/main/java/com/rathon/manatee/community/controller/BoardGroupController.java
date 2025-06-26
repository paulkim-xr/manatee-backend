package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.service.BoardGroupService;
import com.rathon.manatee.community.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boardgroup")
public class BoardGroupController {
    private final BoardGroupService gService;
    private final BoardService bService;

    public BoardGroupController(BoardGroupService gService, BoardService bService) {
        this.gService = gService;
        this.bService = bService;
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<?> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(gService.getChildren(id));
    }
}
