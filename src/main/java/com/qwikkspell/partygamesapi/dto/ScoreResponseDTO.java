package com.qwikkspell.partygamesapi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreResponseDTO {

    private Long scoreId;

    private Double scoreValue;

    private LocalDateTime achievedAt;

    private String playerUuid;

    private String playerUsername;

    private String gameName;

    private String gameDescription;
}
