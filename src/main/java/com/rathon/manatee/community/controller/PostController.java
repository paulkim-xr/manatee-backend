package com.rathon.manatee.community.controller;

import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.service.PostService;
import com.rathon.manatee.database.dto.PagedDtoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
