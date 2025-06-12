package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.IndustryDto;
import com.rathon.manatee.database.model.Industry;
import com.rathon.manatee.database.service.IndustryService;
import com.rathon.manatee.database.service.mapper.IndustryMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {
    private final IndustryService service;
    private final IndustryMapperService mapper;

    public IndustryController(IndustryService service, IndustryMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<IndustryDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDto> getById(@PathVariable Long id) {
        Industry e = service.getIndustryById(id);
        return (e == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(mapper.toDto(e));
    }

    @PostMapping
    public ResponseEntity<Void> createIndustry(@RequestBody IndustryDto d) {
        Industry e = mapper.toEntity(d);
        service.createIndustry(e);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIndustry(@PathVariable Long id, @RequestBody IndustryDto d) {
        Industry c = mapper.toEntity(d);
        service.updateIndustry(c);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIndustry(@PathVariable Long id) {
        service.deleteIndustry(id);
        return ResponseEntity.ok().build();
    }
}
