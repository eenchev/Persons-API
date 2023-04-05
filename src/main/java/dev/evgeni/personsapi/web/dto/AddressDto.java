package dev.evgeni.personsapi.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {
    @NotNull
    private String street;
    @NotNull
    private int number;
}
