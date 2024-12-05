package com.qwikkspell.partygamesapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
