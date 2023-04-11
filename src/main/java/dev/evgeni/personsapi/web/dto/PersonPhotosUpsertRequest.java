package dev.evgeni.personsapi.web.dto;

import java.util.Set;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
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
public class PersonPhotosUpsertRequest {

    @NotNull
    private Set<UUID> personPhotoIds;

}
