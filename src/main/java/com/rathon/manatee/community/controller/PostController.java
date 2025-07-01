package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.service.PostService;
import com.rathon.manatee.database.dto.PagedDtoList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public PagedDtoList<PostSummaryDto> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return service.getPagedPosts(page, size, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPostById(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(service.getComments(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedDtoList<PostSummaryDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") Integer option,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.search(page, size, sort, query, option));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PostDto d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PostDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
