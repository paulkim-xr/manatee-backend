package com.rathon.manatee.core.controller;

import com.rathon.manatee.core.dto.ObjectDto;
import com.rathon.manatee.core.dto.PagedDtoList;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ObjectController<T> {
    private final ObjectService<T> service;

    public ObjectController(ObjectService<T> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PagedDtoList<ObjectDto<T>>> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }
}
