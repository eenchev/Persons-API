package dev.evgeni.personsapi.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.models.Photo;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, UUID> {
    
}
