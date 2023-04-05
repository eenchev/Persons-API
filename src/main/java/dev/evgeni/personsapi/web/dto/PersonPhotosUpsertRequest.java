package dev.evgeni.personsapi.web.dto;

import java.util.Set;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonPhotosUpsertRequest {

    @NotNull
    private Set<UUID> personPhotoIds;

}
