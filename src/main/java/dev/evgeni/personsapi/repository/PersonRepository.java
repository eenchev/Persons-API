package dev.evgeni.personsapi.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.models.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {


    // Enables the distinct flag for the query
    List<Person> findDistinctPeopleByNameOrEgnNumber(String name, String egnNumber);

    // Enabling ignoring case for an individual property
    List<Person> findByNameIgnoreCase(String name);

    // Enabling ignoring case for all suitable properties
    List<Person> findByNameAndEgnNumberAllIgnoreCase(String name, String egnNumber);

    // Enabling static ORDER BY for a query
    List<Person> findByNameOrderByNameAsc(String name);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN
            TRUE ELSE FALSE END
            FROM Person p
            WHERE p.egnNumber = ?1
            """
    )
    boolean existByEgnNumber(String egnNumber);

}
