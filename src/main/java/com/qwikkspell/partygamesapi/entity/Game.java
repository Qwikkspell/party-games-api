package com.qwikkspell.partygamesapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @Column(name = "game_name", nullable = false, unique = true, length = 50)
    private String gameName;

    @Column(name = "description", nullable = false, length = 255)
    private String description;
}
