package dev.evgeni.personsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import dev.evgeni.personsapi.model.Person;
import dev.evgeni.personsapi.web.dto.PersonCreateRequest;
import dev.evgeni.personsapi.web.dto.PersonResponse;
import dev.evgeni.personsapi.web.dto.PersonUpdateRequest;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface PersonMapper {
    Person modelFromCreateRequest(PersonCreateRequest personCreate);
    PersonResponse responseFromModel(Person person);
    void modelFromUpdateRequest(PersonUpdateRequest personUpdate, @MappingTarget Person person);
}
