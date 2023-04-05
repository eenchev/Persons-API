package dev.evgeni.personsapi.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

}
