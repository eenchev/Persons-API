package dev.evgeni.personsapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import dev.evgeni.personsapi.error.NotFoundObjectException;
import dev.evgeni.personsapi.model.Person;
import dev.evgeni.personsapi.model.Photo;
import dev.evgeni.personsapi.repository.PersonPagingRepository;
import dev.evgeni.personsapi.repository.PersonRepository;
import dev.evgeni.personsapi.repository.PhotoRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private PersonPagingRepository personPagingRepo;

    public Page<Person> findAllPaginated(Integer page, Integer pageSize) {
        return personPagingRepo.findAll(PageRequest.of(page, pageSize));
    }

    public Person findById(UUID personId) {
        return personRepo.findById(personId).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });
    }

    public void deleteById(UUID id) {
        personRepo.deleteById(id);
    }

    public Person save(Person person) {
        return personRepo.save(person);
    }

    public Set<UUID> getPersonPhotoIds(UUID personId) {
        Person person = this.findById(personId);

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : person.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        return allPersonPhotoIds;
    }

    public Set<UUID> setPersonPhotos(UUID personId, Set<UUID> photoIds) {
        Person person = this.findById(personId);

        List<Photo> allPersonPhotos = (List<Photo>) photoRepo.findAllById(photoIds);

        person.setPhotos(new HashSet<>(allPersonPhotos));
        Person savedPerson = this.save(person);

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : savedPerson.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        return allPersonPhotoIds;

    }

}
