package dev.evgeni.personsapi.web.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private UUID id;
    private String name;
    private int age;
    private AddressDto address;
    private String egnNumber;
    private String gender;
}
