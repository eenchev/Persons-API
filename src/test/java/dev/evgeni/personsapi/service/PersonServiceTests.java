package dev.evgeni.personsapi.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.evgeni.personsapi.model.Address;
import dev.evgeni.personsapi.model.Gender;
import dev.evgeni.personsapi.model.Person;
import dev.evgeni.personsapi.repository.CommentRepository;
import dev.evgeni.personsapi.repository.PersonPagingRepository;
import dev.evgeni.personsapi.repository.PersonRepository;
import dev.evgeni.personsapi.repository.PhotoRepository;

@ExtendWith(MockitoExtension.class) 
public class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PersonPagingRepository personPagingRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void savePerson() {
        Address personAddress = Address.builder().street("Aleksi Rilets").number(99).build();

        Person person = Person.builder()
        .age(10)
        .egnNumber("9999999999")
        .gender(Gender.MALE)
        .address(personAddress)
        .build();

        personService.save(person);

        verify(personRepository, times(1)).save(person);
    }
    
}
