package dev.evgeni.personsapi.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String street;
    private int number;
}
