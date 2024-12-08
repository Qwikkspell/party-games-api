package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.entity.Score;
import com.qwikkspell.partygamesapi.repository.GameRepository;
import com.qwikkspell.partygamesapi.repository.PlayerRepository;
import com.qwikkspell.partygamesapi.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    private Score testScore;
    private Player testPlayer;
    private Game testGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPlayer = new Player("51952f43-27c7-4275-a29b-55c8d2c52a2b", "Qwikkspell", LocalDateTime.now());
        testGame = new Game("MazeRunner", "Run through a maze");
        testScore = new Score(1L, 1500.0, LocalDateTime.now(), testPlayer, testGame);

        when(playerRepository.findById(testPlayer.getUuid())).thenReturn(Optional.of(testPlayer));
        when(gameRepository.findById(testGame.getGameName())).thenReturn(Optional.of(testGame));
        when(scoreRepository.save(any(Score.class))).thenReturn(testScore);
        when(scoreRepository.findById(1L)).thenReturn(Optional.of(testScore));
        when(scoreRepository.findTop5ByGame_GameNameOrderByScoreValueDesc("MazeRunner"))
                .thenReturn(List.of(testScore));
        when(scoreRepository.findFirstByPlayer_UuidAndGame_GameNameOrderByScoreValueDesc(
                testPlayer.getUuid(), testGame.getGameName()))
                .thenReturn(Optional.of(testScore));
    }

    @Test
    void testAddScore() {
        Score savedScore = scoreService.addScore(testScore);

        assertNotNull(savedScore);
        assertEquals(1500.0, savedScore.getScoreValue());
        verify(playerRepository, times(1)).findById(testPlayer.getUuid());
        verify(gameRepository, times(1)).findById(testGame.getGameName());
        verify(scoreRepository, times(1)).save(testScore);
    }

    @Test
    void testGetTopScores() {
        List<Score> topScores = scoreService.getTopScores("MazeRunner", 5);

        assertEquals(1, topScores.size());
        assertEquals(testScore, topScores.get(0));
        verify(scoreRepository, times(1)).findTop5ByGame_GameNameOrderByScoreValueDesc("MazeRunner");
    }

    @Test
    void testGetBestScore() {
        Optional<Score> bestScore = scoreService.getBestScore(testPlayer.getUuid(), testGame.getGameName());

        assertTrue(bestScore.isPresent());
        assertEquals(testScore, bestScore.get());
        verify(scoreRepository, times(1)).findFirstByPlayer_UuidAndGame_GameNameOrderByScoreValueDesc(
                testPlayer.getUuid(), testGame.getGameName());
    }

    @Test
    void testDeleteScore() {
        scoreService.deleteScore(1L);

        verify(scoreRepository, times(1)).delete(testScore);
    }

    @Test
    void testDeleteScoreNotFound() {
        when(scoreRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> scoreService.deleteScore(2L));

        assertEquals("Score not found with id 2", exception.getMessage());
        verify(scoreRepository, times(0)).delete(any(Score.class));
    }

    @Test
    void testUpdateScore() {
        Score updatedScore = new Score(1L, 1800.0, LocalDateTime.now(), testPlayer, testGame);
        when(scoreRepository.save(any(Score.class))).thenReturn(updatedScore);

        Score result = scoreService.updateScore(1L, updatedScore);

        assertEquals(1800.0, result.getScoreValue());
        verify(scoreRepository, times(1)).save(updatedScore);
    }

    @Test
    void testUpdateScoreNotFound() {
        when(scoreRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> scoreService.updateScore(2L, testScore));

        assertEquals("Score not found with id 2", exception.getMessage());
        verify(scoreRepository, times(0)).save(any(Score.class));
    }
}
