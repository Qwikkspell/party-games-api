package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Score;
import com.qwikkspell.partygamesapi.repository.GameRepository;
import com.qwikkspell.partygamesapi.repository.PlayerRepository;
import com.qwikkspell.partygamesapi.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {
    public ScoreRepository scoreRepository;
    public PlayerRepository playerRepository;
    public GameRepository gameRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public Score addScore(Score score) {
        playerRepository.findById(score.getPlayer().getUuid())
                .orElseThrow(() -> new RuntimeException("Player not found with UUID " + score.getPlayer().getUuid()));

        gameRepository.findById(score.getGame().getGameName())
                .orElseThrow(() -> new RuntimeException("Game not found with name " + score.getGame().getGameName()));

        return scoreRepository.save(score);
    }

    @Override
    public List<Score> getTopScores(String gameName, int limit) {
        return scoreRepository.findTop5ByGame_GameNameOrderByScoreValueDesc(gameName);
    }

    @Override
    public Optional<Score> getBestScore(String playerUuid, String gameName) {
        return scoreRepository.findFirstByPlayer_UuidAndGame_GameNameOrderByScoreValueDesc(playerUuid, gameName);
    }

    @Override
    public Score updateScore(Long scoreId, Score scoreDetails) {
        Score existingScore = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found with id " + scoreId));

        existingScore.setScoreValue(scoreDetails.getScoreValue());
        existingScore.setAchievedAt(scoreDetails.getAchievedAt());
        return scoreRepository.save(existingScore);
    }

    @Override
    public void deleteScore(Long scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found with id " + scoreId));
        scoreRepository.delete(score);

    }

    @Override
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
}
