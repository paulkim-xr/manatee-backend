package com.rathon.manatee.database.controller;

import com.rathon.manatee.auth.dto.UserGroupDto;
import com.rathon.manatee.auth.service.UserGroupService;
import com.rathon.manatee.core.controller.ObjectController;
import com.rathon.manatee.database.dto.EmployeeDto;
import com.rathon.manatee.core.dto.PagedList;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.EmployeeService;
import com.rathon.manatee.database.service.mapper.EmployeeMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends ObjectController<Employee, EmployeeDto, EmployeeService> {
    private final EmployeeMapperService mapper;
    private final UserGroupService userGroupService;

    public EmployeeController(EmployeeService service, EmployeeMapperService mapper, UserGroupService userGroupService) {
        super(service);
        this.mapper = mapper;
        this.userGroupService = userGroupService;
    }

    @GetMapping
    public ResponseEntity<PagedList<EmployeeDto>> getPagedObjects(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getPagedEmployees(page, size, sort));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAll(@RequestParam(required = false) Boolean root) {
        return super.getAll(root);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return super.getCount();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getObject(@PathVariable Long id) {
        return super.getObject(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedList<EmployeeDto>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String dob,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort
    ) {
        PagedList<EmployeeDto> pagedList;
        if (query != null) {
            pagedList = service.search(query, page, size, sort);
        } else {
            pagedList = service.search(company, unit, lastName, firstName, name, position, email, phone, dob, page, size, sort);
        }

        return ResponseEntity.ok(pagedList);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody EmployeeDto d) {
        Employee e = mapper.toEntity(d);
        // TODO - add logic to check required fields
        service.insert(e);

        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @Override
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EmployeeDto d) {
        Employee c = mapper.toEntity(d);
        service.update(c); // TODO
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @GetMapping("/validate/username")
    public ResponseEntity<?> checkUniqueUsername(@RequestParam String username) {
        if (service.checkUniqueUsername(username)) return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<UserGroupDto>> getGroups(@PathVariable Long id) {
        return ResponseEntity.ok(userGroupService.findGroupsByUserId(id));
    }

    @PutMapping("/{id}/groups")
    public ResponseEntity<List<UserGroupDto>> setGroups(@PathVariable Long id, @RequestBody Long[] ids) {
        service.setGroups(id, ids);
        return ResponseEntity.ok().build();
    }
}
