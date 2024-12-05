package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @BeforeEach
    void setUp() {
        Game game = new Game("MazeRunner", "Navigate the maze as quickly as possible");
        when(gameService.getGameByName("MazeRunner")).thenReturn(Optional.of(game));
        when(gameService.getAllGames()).thenReturn(List.of(game));
    }

    @Test
    void testGetAllGames() throws Exception {
        when(gameService.getAllGames()).thenReturn(List.of(
                new Game("MazeRunner", "Navigate the maze as quickly as possible"),
                new Game("PuzzleCraze", "Solve the puzzles to win")
        ));

        mockMvc.perform(get("/api/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].gameName").value("MazeRunner"))
                .andExpect(jsonPath("$[0].description").value("Navigate the maze as quickly as possible"))
                .andExpect(jsonPath("$[1].gameName").value("PuzzleCraze"))
                .andExpect(jsonPath("$[1].description").value("Solve the puzzles to win"));

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    void testGetGameByName() throws Exception {
        when(gameService.getGameByName("MazeRunner"))
                .thenReturn(Optional.of(new Game("MazeRunner", "Navigate the maze as quickly as possible")));

        mockMvc.perform(get("/api/games/MazeRunner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName").value("MazeRunner"))
                .andExpect(jsonPath("$.description").value("Navigate the maze as quickly as possible"));

        verify(gameService, times(1)).getGameByName("MazeRunner");
    }

    @Test
    void testCreateGame() throws Exception {
        String gameJson = """
            {
                "gameName": "MazeRunner",
                "description": "Navigate the maze as quickly as possible"
            }
        """;

        Game newGame = new Game("MazeRunner", "Navigate the maze as quickly as possible");
        when(gameService.createGame(any(Game.class))).thenReturn(newGame);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameName").value("MazeRunner"))
                .andExpect(jsonPath("$.description").value("Navigate the maze as quickly as possible"));

        verify(gameService, times(1)).createGame(any(Game.class));
    }

    @Test
    void testDeleteGame() throws Exception {
        doNothing().when(gameService).deleteGame("MazeRunner");

        mockMvc.perform(delete("/api/games/MazeRunner"))
                .andExpect(status().isNoContent());

        verify(gameService, times(1)).deleteGame("MazeRunner");
    }
}
