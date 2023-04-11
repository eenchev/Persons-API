package dev.evgeni.personsapi.web.dto;

import org.hibernate.validator.constraints.Range;
import dev.evgeni.personsapi.model.Gender;
import dev.evgeni.personsapi.validation.ValidEgn;
import dev.evgeni.personsapi.validation.ValidEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateRequest {

    private String name;
    @Range(min = 0, max = 200, message = "i like ages from 0 to 200")
    private int age;

    @Valid
    private AddressDto address;

    @ValidEgn(message = "Persons EGN should have exactly 10 chars")
    private String egnNumber;

    @ValidEnum(enumClazz = Gender.class)
    private String gender;

}
