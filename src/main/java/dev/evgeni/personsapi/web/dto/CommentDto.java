package dev.evgeni.personsapi.web.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull
    @NotBlank
    String content;

    private LocalDateTime created;
    private LocalDateTime modified;
}
