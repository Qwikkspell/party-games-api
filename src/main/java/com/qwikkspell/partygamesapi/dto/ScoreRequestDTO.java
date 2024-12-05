package com.qwikkspell.partygamesapi.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRequestDTO {

    @NotNull(message = "Score value is required")
    @Min(value = 0, message = "Score value must be positive")
    private Double scoreValue;

    @NotNull(message = "Achievement timestamp is required")
    private LocalDateTime achievedAt;

    @NotBlank(message = "Player UUID is required")
    private String playerUuid;

    @NotBlank(message = "Game name is required")
    private String gameName;
}
