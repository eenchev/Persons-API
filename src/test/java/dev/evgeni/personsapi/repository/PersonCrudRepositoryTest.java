package dev.evgeni.personsapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import dev.evgeni.personsapi.models.Person;

@DataJpaTest
public class PersonCrudRepositoryTest {

    @Autowired
    private PersonRepository repo;

    @Autowired
    private TestEntityManager em;

    @Test
    @Sql("classpath:db/singlePerson.sql")
    void shouldFetchPersonByIdFromDb() {
        String existingUUID = "1931a823-7d63-4fc9-aa7e-83fda3915b1d";
        Optional<Person> personOptional = repo.findById(UUID.fromString(existingUUID));

        assertTrue(personOptional.isPresent());

        Person person = personOptional.get();

        assertEquals(person.getAge(), Integer.valueOf(15));
    }

    @Test
    void shouldFetchPersonByIdPersistedViaEM() {

        UUID personId = UUID.randomUUID();
        Person person = Person.builder()
                .name("Pesho")
                .age(30)
                .address(null)
                .build();


        Person persistedPerson =  em.persist(person);

        assertTrue(repo.findById(persistedPerson.getId()).isPresent());
    }


    @Test
    void shouldExistPersonByEgnNumber() {

        String egnNumber = "7777777777";
        String invalidEgnNumber = "66";
        Person person = Person.builder()
                .name("Pesho")
                .age(30)
                .egnNumber(egnNumber)
                .address(null)
                .build();


        Person persistedPerson =  em.persist(person);

        assertTrue(repo.existByEgnNumber(egnNumber));
        assertFalse(repo.existByEgnNumber(invalidEgnNumber));
    }
    
}
