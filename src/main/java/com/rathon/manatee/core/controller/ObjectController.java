package com.rathon.manatee.core.controller;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.PagedDtoList;
import com.rathon.manatee.core.service.ObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ObjectController<T> {
    private final ObjectService<T> service;

    public ObjectController(ObjectService<T> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PagedDtoList<Dto<T>>> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dto<T>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dto<T>> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedDtoList<Dto<T>>> search(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Dto<T> d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Dto<T> d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
