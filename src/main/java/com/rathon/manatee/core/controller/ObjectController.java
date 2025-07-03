package com.rathon.manatee.core.controller;

import com.rathon.manatee.core.dto.Dto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.core.service.mapper.ObjectMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ObjectController<T, D extends Dto<T>, S extends ObjectService<T, D, ? extends ObjectMapper<T, D>, ? extends ObjectMapperService<T, D>>> {
    public final S service;

    public ObjectController(S service) {
        this.service = service;
    }

//    @GetMapping
    public ResponseEntity<PagedList<D>> getPaged(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(service.getPagedObjects(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<D>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getObject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getObjectById(id));
    }

//    @GetMapping("/search")
    public ResponseEntity<PagedList<D>> searchTemplate(
            @RequestParam(required = false) String sort,
            // Columns...
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> insert(@RequestBody D d) {
        service.insert(d);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> update(@RequestBody D d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
