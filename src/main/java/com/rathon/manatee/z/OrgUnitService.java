package com.rathon.manatee.z;

import org.springframework.stereotype.Service;

@Service
public class OrgUnitService {

    private final OrgUnitRepository repo;

    public OrgUnitService(OrgUnitRepository repo) {
        this.repo = repo;
    }

    public OrgUnit saveOrgUnit(OrgUnit unit) {
        if (unit.getParent() != null && unit.getId() != null) {
            if (unit.getId().equals(unit.getParent().getId())) {
                throw new IllegalArgumentException("Parent node cannot be itself");
            }
            checkNoCycle(unit);
        }

        return repo.save(unit);
    }

    private void checkNoCycle(OrgUnit unit) {
        OrgUnit current = unit.getParent();

        while (current != null) {
            if (current.getId().equals(unit.getId())) {
                throw new IllegalArgumentException("Node hierarchy is cyclical");
            }
            current = current.getParent();
        }
    }
}
