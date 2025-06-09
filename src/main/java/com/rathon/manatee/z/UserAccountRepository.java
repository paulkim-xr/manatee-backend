package com.rathon.manatee.z;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long>, CrudRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
}
