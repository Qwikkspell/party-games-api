package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.entity.Score;
import com.qwikkspell.partygamesapi.service.ScoreService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(ScoreController.class)
class ScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        when(scoreService.getAllScores()).thenReturn(List.of(
                new Score(1L, 1000.0, LocalDateTime.now(), new Player("player-uuid", "PlayerOne", LocalDateTime.now()), new Game("MazeRunner", "Run through a maze"))
        ));
    }

    @Test
    void testGetAllScores() throws Exception {
        mockMvc.perform(get("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(scoreService, times(1)).getAllScores();
    }
}
