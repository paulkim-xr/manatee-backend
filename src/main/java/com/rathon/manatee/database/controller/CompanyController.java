package com.rathon.manatee.database.controller;

import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.core.dto.IdNameDto;
import com.rathon.manatee.database.dto.CompanyDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Company;
import com.rathon.manatee.database.model.UnitType;
import com.rathon.manatee.database.service.CompanyService;
import com.rathon.manatee.database.service.UnitService;
import com.rathon.manatee.database.service.mapper.CompanyMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController extends ObjectController<Company, CompanyDto, CompanyService> {
    private final UnitService uService;
    private final CompanyMapperService cMapper;

    public CompanyController(CompanyService service, UnitService uService, CompanyMapperService cMapper) {
        super(service);
        this.uService = uService;
        this.cMapper = cMapper;
    }

    @GetMapping
    public PagedList<CompanyDto> getPaged(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return service.getPagedObjects(page, size, sort);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyDto>> getAll(@RequestParam(required = false) Boolean root) {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getObject(@PathVariable Long id) {
        return super.getObject(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<CompanyDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        PagedList<CompanyDto> pagedList;
        if (query != null) {
            pagedList = service.search(query, page, size, sort);
        } else {
            pagedList = service.search(name, address, industry, registrationNumber, page, size, sort);
        }

        return ResponseEntity.ok(pagedList);
    }

    @PreAuthorize("hasAuthority('company_add')")
    @Override
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody CompanyDto d) {
        Company c = cMapper.toEntity(d);
        service.insert(c);
        UnitDto u = new UnitDto();
        u.setCompany(new IdNameDto(c.getId(), c.getName()));
        u.setName(c.getName());
        u.setType(new UnitType(1L, ""));
        uService.insert(u);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('company_edit')")
    @Override
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody CompanyDto d) {
        service.update(d);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('company_delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteA(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (Error error) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.ok().build();
    }

//    ---------------------------------------------------------

    @GetMapping("/{id}/root")
    public ResponseEntity<UnitDto> getRoot(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoot(id));
    }

    @GetMapping("/{id}/units")
    public ResponseEntity<List<UnitDto>> getUnits(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean root) {
        if (service.getObjectById(id) == null) return ResponseEntity.badRequest().build();

        List<UnitDto> list =  service.getUnits(id, root);

        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDto> getEmployees(@PathVariable Long id) {
        return service.getEmployees(id);
    }
}
