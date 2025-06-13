package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.model.Position;
import com.rathon.manatee.database.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService service;

    public PositionController(PositionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Position> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getById(@PathVariable Long id) {
        Position p = service.getPositionById(id);
        return (p == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Void> createPosition(@RequestBody Position p) {
        service.createPosition(p);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable Long id, @RequestBody Position p) {
        service.updatePosition(p);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable Long id) {
        service.deletePosition(id);
        return ResponseEntity.ok().build();
    }
}
