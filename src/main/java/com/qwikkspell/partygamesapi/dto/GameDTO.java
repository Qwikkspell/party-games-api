package com.qwikkspell.partygamesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {
    @NotBlank(message = "Game name is mandatory")
    @Size(max = 50, message = "Game name must not exceed 50 characters")
    private String gameName;

    @NotBlank(message = "Game description is mandatory")
    @Size(max = 255, message = "Game description must not exceed 255")
    private String description;
}
