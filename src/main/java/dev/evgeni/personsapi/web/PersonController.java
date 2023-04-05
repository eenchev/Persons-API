package dev.evgeni.personsapi.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.evgeni.personsapi.error.InvalidObjectException;
import dev.evgeni.personsapi.error.NotFoundObjectException;
import dev.evgeni.personsapi.models.Person;
import dev.evgeni.personsapi.models.Photo;
import dev.evgeni.personsapi.repository.PersonRepository;
import dev.evgeni.personsapi.repository.PhotoRepository;
import dev.evgeni.personsapi.validation.ObjectValidator;
import dev.evgeni.personsapi.web.dto.PersonCreateRequest;
import dev.evgeni.personsapi.web.dto.PersonPhotosGetResponse;
import dev.evgeni.personsapi.web.dto.PersonPhotosUpsertRequest;


@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository repo;

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private ObjectValidator validator;

    @GetMapping("")
    public List<Person> getAllPersons() {
        return (List<Person>) repo.findAll();
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable String personId) {
        return repo.findById(UUID.fromString(personId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });
    }

    @DeleteMapping("/{personId}")
    public void deletePersonById(@PathVariable String personId) {
        repo.deleteById(UUID.fromString(personId));
    }

    @PostMapping("")
    public ResponseEntity<Person> createPerson(@RequestBody PersonCreateRequest personDto) {

        Map<String, String> validationErrors = validator.validate(personDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Create", validationErrors);
        }

        Person mappedPerson = Person.builder().name(personDto.getName()).age(personDto.getAge())
                .address(personDto.getAddress()).egnNumber(personDto.getEgnNumber()).build();

        Person savedPerson = repo.save(mappedPerson);

        return ResponseEntity.status(201).body(savedPerson);
    }

    @GetMapping("/{personId}/photos")
    public PersonPhotosGetResponse getAllPersonPhotos(@PathVariable String personId) {

        Person person = repo.findById(UUID.fromString(personId)).get();

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : person.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        PersonPhotosGetResponse response =
                PersonPhotosGetResponse.builder().personPhotoIds(allPersonPhotoIds).build();

        return response;
    }

    @PutMapping(value = "/{personId}/photos")
    public PersonPhotosGetResponse setPersonPhotos(@PathVariable String personId,
            @RequestBody PersonPhotosUpsertRequest request) {
        Person person = repo.findById(UUID.fromString(personId)).get();

        Map<String, String> validationErrors = validator.validate(request);

        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Photos Upsert Request",
                    validationErrors);
        }

        List<Photo> allPersonPhotos =
                (List<Photo>) photoRepo.findAllById(request.getPersonPhotoIds());

        person.setPhotos(new HashSet<>(allPersonPhotos));
        Person savedPerson = repo.save(person);

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : savedPerson.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        PersonPhotosGetResponse response =
                PersonPhotosGetResponse.builder().personPhotoIds(allPersonPhotoIds).build();

        return response;
    }

}
