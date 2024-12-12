package com.qwikkspell.partygamesapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", nullable = false, unique = true)
    private Long scoreId;

    @Column(name = "score_value", nullable = false)
    private Double scoreValue;

    @Column(name = "achieved_at", nullable = false)
    private LocalDateTime achievedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_uuid", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_name", referencedColumnName = "game_name", nullable = false)
    private Game game;


}
