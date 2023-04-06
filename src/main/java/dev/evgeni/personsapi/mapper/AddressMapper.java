package dev.evgeni.personsapi.mapper;

import org.mapstruct.Mapper;
import dev.evgeni.personsapi.model.Address;
import dev.evgeni.personsapi.web.dto.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto modelToDto(Address address);

    Address dtoToModel(AddressDto address);
}
