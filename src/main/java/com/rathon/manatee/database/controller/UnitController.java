package com.rathon.manatee.database.controller;

import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.mapper.UnitMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController extends ObjectController<Unit, UnitDto, UnitService> {
    public UnitController(UnitService service, UnitMapperService mapper) {
        super(service);
    }

    @GetMapping
    public PagedList<UnitDto> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return service.getPagedObjects(page, size, sort);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UnitDto>> getAll(@RequestParam(required = false, defaultValue = "false") Boolean root) {
        return ResponseEntity.ok(service.getAll(root));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@PathVariable Long id) {
//        if (uService.getEmployees(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.getEmployees(id));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<UnitDto>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildren(id));
    }

    @GetMapping("/{id}/tree")
    public ResponseEntity<List<UnitDto>> getFullPath(@PathVariable Long id) {
        List<UnitDto> parents = new ArrayList<>();
        parents.add(service.getObjectById(id));
        UnitDto unit = service.getParent(id);
        while (unit != null) {
            parents.add(unit);
            unit = service.getParent(unit.getId());
        }
        return ResponseEntity.ok(parents);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UnitDto d) {
        return super.insert(d);
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UnitDto d) {
        return super.update(d);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable Long id) {
        List<EmployeeDto> list = service.getEmployees(id);
        if (list.size() > 1) return ResponseEntity.badRequest().body("Remove all employees to delete");

        List<UnitDto> children = service.getChildren(id);
        if (!children.isEmpty()) return ResponseEntity.badRequest().body("Remove all child units to delete");

        service.delete(id);
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
            pagedList = service.search(query, page, size, sort);
        } else {
            pagedList = service.search(name, company, type, code, parent, page, size, sort);
        }

        return ResponseEntity.ok(pagedList);
    }
}
