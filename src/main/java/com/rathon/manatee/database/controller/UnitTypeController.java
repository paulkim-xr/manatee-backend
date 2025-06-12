package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.UnitTypeDto;
import com.rathon.manatee.database.model.UnitType;
import com.rathon.manatee.database.service.UnitTypeService;
import com.rathon.manatee.database.service.mapper.UnitTypeMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit-types")
public class UnitTypeController {
    private final UnitTypeService service;
    private final UnitTypeMapperService mapper;

    public UnitTypeController(UnitTypeService service, UnitTypeMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UnitTypeDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitTypeDto> getById(@PathVariable Long id) {
        UnitType e = service.getUnitTypeById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(mapper.toDto(e));
    }

    @PostMapping
    public ResponseEntity<Void> createUnitType(@RequestBody UnitTypeDto d) {
        UnitType e = mapper.toEntity(d);
        service.createUnitType(e);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUnitType(@PathVariable Long id, @RequestBody UnitTypeDto d) {
        UnitType c = mapper.toEntity(d);
        service.updateUnitType(c);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitType(@PathVariable Long id) {
        service.deleteUnitType(id);
        return ResponseEntity.ok().build();
    }
}
