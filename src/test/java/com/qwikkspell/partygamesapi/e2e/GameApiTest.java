package com.qwikkspell.partygamesapi.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetAllGames() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testCreateGame() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "gameName": "MazeRunner",
                    "description": "Navigate the maze as quickly as possible"
                }
            """)
                .when()
                .post("/api/games")
                .then()
                .statusCode(201)
                .body("gameName", equalTo("MazeRunner"))
                .body("description", equalTo("Navigate the maze as quickly as possible"));
    }

    @Test
    void testGetGameByName() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/games/MazeRunner")
                .then()
                .statusCode(200)
                .body("gameName", equalTo("MazeRunner"))
                .body("description", equalTo("Navigate the maze as quickly as possible"));
    }

    @Test
    void testDeleteGame() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/games/MazeRunner")
                .then()
                .statusCode(204);
    }
}
