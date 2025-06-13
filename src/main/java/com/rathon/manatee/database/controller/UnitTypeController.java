package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.model.UnitType;
import com.rathon.manatee.database.service.UnitTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit-types")
public class UnitTypeController {
    private final UnitTypeService service;

    public UnitTypeController(UnitTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<UnitType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitType> getById(@PathVariable Long id) {
        UnitType t = service.getUnitTypeById(id);
        return (t == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    @PostMapping
    public ResponseEntity<Void> createUnitType(@RequestBody UnitType t) {
        service.createUnitType(t);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUnitType(@PathVariable Long id, @RequestBody UnitType t) {
        service.updateUnitType(t);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitType(@PathVariable Long id) {
        service.deleteUnitType(id);
        return ResponseEntity.ok().build();
    }
}
