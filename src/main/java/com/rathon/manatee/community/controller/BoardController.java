package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.service.BoardService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController extends ObjectController<Board, BoardDto, BoardService> {
    public BoardController(BoardService service) {
        super(service);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PagedList<PostSummaryDto>> getPosts(
            @PathVariable Long id,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getBoardPosts(id, page, size, sort));
    }

    @GetMapping("/{id}/posts/search")
    public ResponseEntity<PagedList<PostSummaryDto>> searchBoard(
            @PathVariable Long id,
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") Integer option,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.searchBoardPosts(id, page, size, sort, query, option));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BoardDto d) {
        return super.insert(d);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody BoardDto d) {
        return super.update(d);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }
}
