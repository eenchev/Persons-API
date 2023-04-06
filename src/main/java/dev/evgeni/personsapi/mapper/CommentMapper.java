package dev.evgeni.personsapi.mapper;

import java.util.Set;
import org.mapstruct.Mapper;
import dev.evgeni.personsapi.model.Comment;
import dev.evgeni.personsapi.web.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto modelToDto(Comment comment);

    Comment dtoToModel(CommentDto commentDto);

    Set<CommentDto> modelCollectionToDtoCollection(Set<Comment> comments);
}
