package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.community.service.PostService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController extends ObjectController<Post, PostDto, PostService> {
    public PostController(PostService service) {
        super(service);
    }

    @GetMapping
    public PagedList<PostSummaryDto> getPagedSummaries(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return service.getPagedObjects(page, size, sort);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(service.getComments(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<PostSummaryDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") Integer option,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.search(page, size, sort, query, option));
    }

    @PreAuthorize("authentication.getName() == #dto.author.username")
    @Override
    @PostMapping
    public ResponseEntity<Void> insert(PostDto dto) {
        return super.insert(dto);
    }

    @PreAuthorize("authentication.getName() == #dto.author.username")
    @Override
    @PutMapping
    public ResponseEntity<Void> update(PostDto dto) {
        return super.update(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or @postSecurity.ownsEntity(#id, authentication)")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }
}
