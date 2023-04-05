package dev.evgeni.personsapi.web.dto;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonPhotosGetResponse {

    private Set<UUID> personPhotoIds;

}
