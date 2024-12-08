package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Score;

import java.util.List;
import java.util.Optional;

public interface ScoreService {
    Score addScore(Score score);
    List<Score> getTopScores(String gameName, int limit);
    Optional<Score> getBestScore(String playerUuid, String gameName);
    Score updateScore(Long scoreId, Score scoreDetails);
    void deleteScore(Long scoreId);
    List<Score> getAllScores();
}
