package dev.evgeni.personsapi.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String token;
}
