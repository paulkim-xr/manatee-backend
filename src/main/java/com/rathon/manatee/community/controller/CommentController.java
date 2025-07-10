package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.CommentService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController extends ObjectController<Comment, CommentDto, CommentService> {
    public CommentController(CommentService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PagedList<CommentDto>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<CommentDto>> search(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("authentication.getName == #dto.author.username")
    @Override
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody CommentDto dto) {
        return super.insert(dto);
    }

    @PreAuthorize("authentication.getName == #dto.author.username")
    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CommentDto dto) {
        return super.update(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or @commentSecurity.ownsEntity(#id, authentication)")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return super.delete(id);
    }
//----------------------------------------------------------------------------------
}
