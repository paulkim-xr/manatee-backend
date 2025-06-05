package com.rathon.manatee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource(collectionResourceRel = "units", path = "units")
public interface OrgUnitRepository extends PagingAndSortingRepository<OrgUnit, Long>, CrudRepository<OrgUnit, Long> {
    List<OrgUnit> findByNameContaining(@Param("name") String name);
    List<OrgUnit> findByName(@Param("name") String name);
}
