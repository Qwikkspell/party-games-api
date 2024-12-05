package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        Player player = new Player("51952f43-27c7-4275-a29b-55c8d2c52a2b", "Qwikkspell", LocalDateTime.now());
        when(playerService.getPlayerByUuid("51952f43-27c7-4275-a29b-55c8d2c52a2b")).thenReturn(Optional.of(player));
        when(playerService.getAllPlayers()).thenReturn(List.of(player));
    }

    @Test
    void testGetPlayerByUuid() throws Exception {
        when(playerService.getPlayerByUuid("player-uuid"))
                .thenReturn(Optional.of(new Player("player-uuid", "PlayerOne", LocalDateTime.now())));

        mockMvc.perform(get("/api/players/player-uuid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value("player-uuid"))
                .andExpect(jsonPath("$.username").value("PlayerOne"));

        verify(playerService, times(1)).getPlayerByUuid("player-uuid");
    }
}
