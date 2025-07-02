package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.service.CommentService;
import com.rathon.manatee.core.controller.ObjectController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController extends ObjectController<Comment, CommentDto, CommentService> {
    public CommentController(CommentService service) {
        super(service);
    }
}
