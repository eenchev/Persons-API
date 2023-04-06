package dev.evgeni.personsapi.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.evgeni.personsapi.error.InvalidObjectException;
import dev.evgeni.personsapi.error.NotFoundObjectException;
import dev.evgeni.personsapi.mapper.PersonMapper;
import dev.evgeni.personsapi.models.Person;
import dev.evgeni.personsapi.models.Photo;
import dev.evgeni.personsapi.repository.PersonPagingRepository;
import dev.evgeni.personsapi.repository.PersonRepository;
import dev.evgeni.personsapi.repository.PhotoRepository;
import dev.evgeni.personsapi.validation.ObjectValidator;
import dev.evgeni.personsapi.web.dto.PersonCreateRequest;
import dev.evgeni.personsapi.web.dto.PersonPhotosGetResponse;
import dev.evgeni.personsapi.web.dto.PersonPhotosUpsertRequest;
import dev.evgeni.personsapi.web.dto.PersonResponse;
import dev.evgeni.personsapi.web.dto.PersonUpdateRequest;


@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository repo;

    @Autowired
    private PersonPagingRepository pagingRepo;

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private ObjectValidator validator;

    @Autowired
    private PersonMapper personMapper;

    private final Integer PAGE_SIZE = 10;

    @GetMapping("")
    public Page<PersonResponse> getAllPersons(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {
        return pagingRepo.findAll(PageRequest.of(currPage, PAGE_SIZE))
                .map(personMapper::responseFromModel);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable String personId) {
        Person person = repo.findById(UUID.fromString(personId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });

        return ResponseEntity.ok(personMapper.responseFromModel(person));
    }

    @DeleteMapping("/{personId}")
    public void deletePersonById(@PathVariable String personId) {
        repo.deleteById(UUID.fromString(personId));
    }

    @PostMapping("")
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonCreateRequest personDto) {

        Map<String, String> validationErrors = validator.validate(personDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Create", validationErrors);
        }

        Person mappedPerson = personMapper.modelFromCreateRequest(personDto);

        Person savedPerson = repo.save(mappedPerson);

        PersonResponse responsePerson = personMapper.responseFromModel(savedPerson);

        return ResponseEntity.status(201).body(responsePerson);
    }

    @PatchMapping("/{personId}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable String personId,
            @RequestBody PersonUpdateRequest personDto) {

        Map<String, String> validationErrors = validator.validate(personDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Create", validationErrors);
        }

        Person currentPerson = repo.findById(UUID.fromString(personId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });

        personMapper.updateModelFromDto(personDto, currentPerson);

        Person updatedPerson = repo.save(currentPerson);

        PersonResponse responsePerson = personMapper.responseFromModel(updatedPerson);

        return ResponseEntity.status(200).body(responsePerson);
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
