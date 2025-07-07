package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.CommentService;
import com.rathon.manatee.core.controller.ObjectController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController extends ObjectController<Comment, CommentDto, CommentService> {
    public CommentController(CommentService service) {
        super(service);
    }

    @PreAuthorize("authentication.getName == #dto.author.username")
    @Override
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody CommentDto dto) {
        return super.insert(dto);
    }

    @PreAuthorize("authentication.getName == #dto.author.username")
    @Override
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody CommentDto dto) {
        return super.update(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or @commentSecurity.ownsEntity(#id, authentication)")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }
}
