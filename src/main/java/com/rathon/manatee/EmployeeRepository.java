package com.rathon.manatee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, CrudRepository<Employee, Long> {
    List<Employee> findByLastNameContaining(@Param("lastname") String lastName);
    List<Employee> findByFirstNameContaining(@Param("firstname") String firstName);
    List<Employee> findByLastName(@Param("lastname") String lastName);
    List<Employee> findByFirstName(@Param("firstname") String firstName);
    Optional<Employee> findById(Long id);
}
