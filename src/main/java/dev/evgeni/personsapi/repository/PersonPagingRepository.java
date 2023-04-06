package dev.evgeni.personsapi.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.model.Person;

@Repository
public interface PersonPagingRepository extends PagingAndSortingRepository<Person, UUID> {
    Page<Person> queryFirst10ByName(String name, Pageable pageable);
}
