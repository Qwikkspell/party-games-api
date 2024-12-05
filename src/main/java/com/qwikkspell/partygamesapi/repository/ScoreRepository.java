package com.qwikkspell.partygamesapi.repository;

import com.qwikkspell.partygamesapi.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findTop5ByGame_GameNameOrderByScoreValueDesc(String gameName);
    Optional<Score> findFirstByPlayer_UuidAndGame_GameNameOrderByScoreValueDesc(String playerUuid, String gameName);
}
