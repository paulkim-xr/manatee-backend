package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.service.BoardService;
import com.rathon.manatee.database.dto.PagedDtoList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PagedDtoList<PostSummaryDto>> getBoard(
            @PathVariable Long id,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getBoard(id, page, size, sort));
    }
}
