package dev.evgeni.personsapi.web;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.evgeni.personsapi.error.InvalidObjectException;
import dev.evgeni.personsapi.mapper.CommentMapper;
import dev.evgeni.personsapi.mapper.PersonMapper;
import dev.evgeni.personsapi.model.Person;
import dev.evgeni.personsapi.service.PersonService;
import dev.evgeni.personsapi.validation.ObjectValidator;
import dev.evgeni.personsapi.web.dto.PersonApiPage;
import dev.evgeni.personsapi.web.dto.PersonCreateRequest;
import dev.evgeni.personsapi.web.dto.PersonPhotosGetResponse;
import dev.evgeni.personsapi.web.dto.PersonPhotosUpsertRequest;
import dev.evgeni.personsapi.web.dto.PersonResponse;
import dev.evgeni.personsapi.web.dto.PersonUpdateRequest;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final ObjectValidator validator;

    private final PersonMapper personMapper;

    private final CommentMapper commentMapper;

    private final Integer PAGE_SIZE = 10;

    @GetMapping("")
    public PersonApiPage<PersonResponse> getAllPersons(
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<PersonResponse> personRepoPage = personService.findAllPaginated(page, PAGE_SIZE)
                .map(personMapper::responseFromModel);
        return new PersonApiPage<PersonResponse>(personRepoPage);
    }

    @GetMapping("/{personId}")
    public PersonResponse getPersonById(@PathVariable String personId) {
        Person person = personService.findById(UUID.fromString(personId));
        return personMapper.responseFromModel(person);
    }

    @DeleteMapping("/{personId}")
    public void deletePersonById(@PathVariable String personId) {
        personService.deleteById(UUID.fromString(personId));
    }

    @PostMapping("")
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonCreateRequest personDto) {

        Map<String, String> validationErrors = validator.validate(personDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Create", validationErrors);
        }

        Person mappedPerson = personMapper.modelFromCreateRequest(personDto);

        Person savedPerson = personService.save(mappedPerson);

        return ResponseEntity.status(201).body(personMapper.responseFromModel(savedPerson));
    }

    @PostMapping("/{personId}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable String personId,
            @RequestBody PersonUpdateRequest personDto) {

        Map<String, String> validationErrors = validator.validate(personDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Update", validationErrors);
        }

        Person currentPerson = personService.findById(UUID.fromString(personId));
        personMapper.modelFromUpdateRequest(personDto, currentPerson);
        Person savedPerson = personService.save(currentPerson);

        return ResponseEntity.status(200).body(personMapper.responseFromModel(savedPerson));
    }

    @GetMapping("/{personId}/photos")
    public PersonPhotosGetResponse getAllPersonPhotos(@PathVariable String personId) {

        Set<UUID> allPersonPhotoIds = personService.getPersonPhotoIds(UUID.fromString(personId));
        PersonPhotosGetResponse response =
                PersonPhotosGetResponse.builder().personPhotoIds(allPersonPhotoIds).build();

        return response;
    }

    @PutMapping(value = "/{personId}/photos")
    public PersonPhotosGetResponse setPersonPhotos(@PathVariable String personId,
            @RequestBody PersonPhotosUpsertRequest request) {

        Map<String, String> validationErrors = validator.validate(request);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Person Photos Upsert Request",
                    validationErrors);
        }

        Set<UUID> allPersonPhotoIds = personService.setPersonPhotos(UUID.fromString(personId),
                request.getPersonPhotoIds());

        PersonPhotosGetResponse response =
                PersonPhotosGetResponse.builder().personPhotoIds(allPersonPhotoIds).build();

        return response;
    }

    // @GetMapping("/{personId}/comments")
    // public Set<CommentDto> getAllPersonComments(@PathVariable String personId) {

    //     Set<Comment> allPersonComments = personService.findById(UUID.fromString(personId)).getComments();

    //     return commentMapper.modelCollectionToDtoCollection(allPersonComments);
    // }

    // @PostMapping("/{personId}/comments")
    // public CommentDto addPersonComment(@PathVariable String personId, @RequestBody CommentDto commentDto) {

    //     Map<String, String> validationErrors = validator.validate(commentDto);
    //     if (validationErrors.size() != 0) {
    //         throw new InvalidObjectException("Invalid Person Comment Add Request",
    //                 validationErrors);
    //     }

    //     Comment commentBase = commentMapper.dtoToModel(commentDto);
    //     Comment savedComment = personService.addCommentForPerson(UUID.fromString(personId), commentBase);

    //     return commentMapper.modelToDto(savedComment);
    // }

}
