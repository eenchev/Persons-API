package dev.evgeni.personsapi.mapper;

import org.mapstruct.Mapper;
import dev.evgeni.personsapi.models.Address;
import dev.evgeni.personsapi.web.dto.AddressDto;

@Mapper
public interface AddressMapper {

    AddressDto modelToDto(Address address);
    Address dtoToModel(AddressDto addressDto);
}
