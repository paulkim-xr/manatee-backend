package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.PositionDto;
import com.rathon.manatee.database.model.Position;
import com.rathon.manatee.database.service.PositionService;
import com.rathon.manatee.database.service.mapper.PositionMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService service;
    private final PositionMapperService mapper;

    public PositionController(PositionService service, PositionMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PositionDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getById(@PathVariable Long id) {
        Position e = service.getPositionById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(mapper.toDto(e));
    }

    @PostMapping
    public ResponseEntity<Void> createPosition(@RequestBody PositionDto d) {
        Position e = mapper.toEntity(d);
        service.createPosition(e);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable Long id, @RequestBody PositionDto d) {
        Position c = mapper.toEntity(d);
        service.updatePosition(c);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable Long id) {
        service.deletePosition(id);
        return ResponseEntity.ok().build();
    }
}
