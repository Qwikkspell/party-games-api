package com.qwikkspell.partygamesapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    @NotBlank(message = "UUID is mandatory")
    private String uuid;

    @NotBlank(message = "username is mandatory")
    @Size(max = 16, message = "username must not exceed 16 characters")
    private String username;

    @NotNull(message = "join date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime joinedAt;
}
