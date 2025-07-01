package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.service.BoardService;
import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.Unit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBoard(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BoardDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PagedDtoList<PostSummaryDto>> getPosts(
            @PathVariable Long id,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getBoardPosts(id, page, size, sort));
    }

    @GetMapping("/{id}/posts/search")
    public ResponseEntity<PagedDtoList<PostSummaryDto>> searchBoard(
            @PathVariable Long id,
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") Integer option,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getBoardPosts(id, page, size, sort, query, option));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BoardDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BoardDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
