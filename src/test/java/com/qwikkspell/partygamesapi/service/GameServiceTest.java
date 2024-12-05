package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game testGame;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testGame = new Game("MazeRunner", "Run through a maze");
    }

    @Test
    void testCreateGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(testGame);

        Game savedGame = gameService.createGame(testGame);

        assertNotNull(savedGame);
        assertEquals("MazeRunner", savedGame.getGameName());
        verify(gameRepository, times(1)).save(testGame);
    }

    @Test
    void testGetGameByName() {
        when(gameRepository.findById("MazeRunner")).thenReturn(Optional.of(testGame));

        Optional<Game> retrievedGame = gameService.getGameByName("MazeRunner");

        assertTrue(retrievedGame.isPresent());
        assertEquals(testGame, retrievedGame.get());
    }

    @Test
    void testDeleteGame() {
        when(gameRepository.findById("MazeRunner")).thenReturn(Optional.of(testGame));

        gameService.deleteGame("MazeRunner");

        verify(gameRepository, times(1)).delete(testGame);
    }
}
