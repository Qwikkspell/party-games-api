package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPlayer = new Player("player-uuid", "PlayerOne", LocalDateTime.now());
    }

    @Test
    void testCreatePlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(testPlayer);

        Player savedPlayer = playerService.createPlayer(testPlayer);

        assertNotNull(savedPlayer);
        assertEquals("PlayerOne", savedPlayer.getUsername());
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    void testGetPlayerByUuid() {
        when(playerRepository.findById("player-uuid")).thenReturn(Optional.of(testPlayer));

        Optional<Player> retrievedPlayer = playerService.getPlayerByUuid("player-uuid");

        assertTrue(retrievedPlayer.isPresent());
        assertEquals(testPlayer, retrievedPlayer.get());
    }

    @Test
    void testDeletePlayer() {
        when(playerRepository.findById("player-uuid")).thenReturn(Optional.of(testPlayer));

        playerService.deletePlayer("player-uuid");

        verify(playerRepository, times(1)).delete(testPlayer);
    }
}
