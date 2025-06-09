package com.rathon.manatee.z;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource
public interface PositionRepository extends PagingAndSortingRepository<Position, Long>, CrudRepository<Position, Long> {
}
