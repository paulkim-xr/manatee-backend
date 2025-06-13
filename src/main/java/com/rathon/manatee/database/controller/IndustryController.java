package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.model.Industry;
import com.rathon.manatee.database.service.IndustryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {
    private final IndustryService service;

    public IndustryController(IndustryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Industry> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Industry> getById(@PathVariable Long id) {
        Industry e = service.getIndustryById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<Void> createIndustry(@RequestBody Industry i) {
        service.createIndustry(i);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIndustry(@PathVariable Long id, @RequestBody Industry i) {
        service.updateIndustry(i);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIndustry(@PathVariable Long id) {
        service.deleteIndustry(id);
        return ResponseEntity.ok().build();
    }
}
