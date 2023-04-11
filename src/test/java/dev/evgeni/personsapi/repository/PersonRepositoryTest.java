package dev.evgeni.personsapi.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import dev.evgeni.personsapi.model.Person;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TestEntityManager entityManager;

    // @Test
    // @Description("Existing Person Can Be Found")
    // @Sql("multiple-persons.sql")
    // void existingPersonCanBeFound() {
    //     Optional<Person> savedPerson = personRepository.findById(UUID.fromString("fa338013-338a-4491-bac1-6cf62ec54fd1"));

    //     assertThat(savedPerson).isPresent();
    //     assertThat(savedPerson.get().getAddress()).isNull();
    //     assertThat(savedPerson.get().getAge()).isEqualTo(12);
    // }

    @Test
    void existsByEgnNumberManualQuery() {
        String egnNumber = "9999999998";
        Person person = Person.builder()
        .name("Ivan")
        .age(15)
        .egnNumber(egnNumber)
        .address(null)
        .build();

        entityManager.persist(person);

        boolean exists = personRepository.existsByEgnNumberManualQuery(egnNumber);

        assertTrue(exists);
    }
    
}
