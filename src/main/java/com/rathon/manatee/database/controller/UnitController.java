package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.database.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    private final UnitService uService;
    private final UnitMapperService uMapper;
    private final EmployeeMapperService eMapper;

    public UnitController(UnitService uService, UnitMapperService uMapper, EmployeeMapperService eMapper) {
        this.uService = uService;
        this.uMapper = uMapper;
        this.eMapper = eMapper;
    }

    @GetMapping
    public PagedList<UnitDto> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return uService.getPagedUnits(page, size, sort);
    }

    @GetMapping("/all")
    public List<UnitDto> getAll(@RequestParam(required = false, defaultValue = "false") Boolean all) {
        return (all != null && all) ? uService.getFullUnitList() : uService.getUnitList();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(uService.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getById(@PathVariable Long id) {
        if (uService.getUnitById(id) == null) return ResponseEntity.notFound().build();
        UnitDto u = uService.getUnitById(id);
        return (u == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(u);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@PathVariable Long id) {
//        if (uService.getEmployees(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(uService.getEmployees(id));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<UnitDto>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(uService.getChildren(id));
    }

    @GetMapping("/{id}/tree")
    public ResponseEntity<List<UnitDto>> getFullPath(@PathVariable Long id) {
        List<UnitDto> parents = new ArrayList<>();
        parents.add(uService.getUnitById(id));
        UnitDto unit = uService.getParent(id);
        while (unit != null) {
            parents.add(unit);
            unit = uService.getParent(unit.getId());
        }
        return ResponseEntity.ok(parents);
    }

    @PostMapping
    public ResponseEntity<Void> createUnit(@RequestBody UnitDto d) {
        Unit u = uMapper.toEntity(d);
        uService.createUnit(u);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUnit(@RequestBody UnitDto d) {
        uService.updateUnit(uMapper.toEntity(d));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable Long id) {
        List<EmployeeDto> list = uService.getEmployees(id);
        if (list.size() > 1) return ResponseEntity.badRequest().body("Remove all employees to delete");

        List<UnitDto> children = uService.getChildren(id);
        if (!children.isEmpty()) return ResponseEntity.badRequest().body("Remove all child units to delete");

        uService.deleteUnit(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<UnitDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String parent,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        PagedList<UnitDto> pagedList = null;
        if (query != null) {
            pagedList = uService.search(query, page, size, sort);
        } else {
            pagedList = uService.search(name, company, type, code, parent, page, size, sort);
        }

        return ResponseEntity.ok(pagedList);
    }
}
