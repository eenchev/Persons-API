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
import dev.evgeni.personsapi.models.Person;
import dev.evgeni.personsapi.models.Photo;
import dev.evgeni.personsapi.repository.PersonPagingRepository;
import dev.evgeni.personsapi.repository.PersonRepository;
import dev.evgeni.personsapi.repository.PhotoRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repo;

    @Autowired
    private PersonPagingRepository pagingRepo;

    @Autowired
    private PhotoRepository photoRepo;

    public Person save(Person person) {
        return repo.save(person);
    }

    public Page<Person> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public Person findById(String personId) {
        return repo.findById(UUID.fromString(personId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });
    }

    public void deleteById(String personId) {
        repo.deleteById(UUID.fromString(personId));
    }

    public Set<UUID> getAllPersonPhotoIds(String personId) {
        Person person = repo.findById(UUID.fromString(personId)).get();

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : person.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        return allPersonPhotoIds;
    }

    public Set<UUID> setPersonPhotos(String personId, Set<UUID> personPhotoIds) {
        Person person = repo.findById(UUID.fromString(personId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Person Not Found", Person.class.getName(), personId);
        });

        List<Photo> allPersonPhotos =
                (List<Photo>) photoRepo.findAllById(personPhotoIds);

        person.setPhotos(new HashSet<>(allPersonPhotos));
        Person savedPerson = repo.save(person);

        Set<UUID> allPersonPhotoIds = new HashSet<>();
        for (Photo photo : savedPerson.getPhotos()) {
            allPersonPhotoIds.add(photo.getId());
        }

        return allPersonPhotoIds;
    }
}
