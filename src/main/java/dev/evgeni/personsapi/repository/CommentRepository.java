package dev.evgeni.personsapi.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.evgeni.personsapi.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, UUID> {

}
