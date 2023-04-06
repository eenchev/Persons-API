package dev.evgeni.personsapi.web.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonResponse {

    private UUID id;
    private String name;
    private int age;
    private AddressDto address;
    private String egnNumber;

}
