package dev.evgeni.personsapi.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.model.Gender;
import dev.evgeni.personsapi.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

    List<Person> findByGender(Gender gender);

    // Enables the distinct flag for the query
    List<Person> findDistinctPeopleByNameOrEgnNumber(String nale, String egnNumber);

    // Enabling ignoring case for an individual property
    List<Person> findByNameIgnoreCase(String name);

    // Enabling ignoring case for all suitable properties
    List<Person> findByNameAndEgnNumberAllIgnoreCase(String name, String egnNumber);

    // Enabling static ORDER BY for a query
    List<Person> findByNameOrderByNameAsc(String name);

    List<Person> findByGenderOrderByNameDesc(Gender gender);

    @Query("select p from Person p where u.egnNumber = ?1")
    Person findByEgnNumber(String egnNumner);

}
