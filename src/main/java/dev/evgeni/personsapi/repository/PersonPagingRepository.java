package dev.evgeni.personsapi.repository;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.models.Person;

@Repository
public interface PersonPagingRepository extends PagingAndSortingRepository<Person, UUID> {
    
}
