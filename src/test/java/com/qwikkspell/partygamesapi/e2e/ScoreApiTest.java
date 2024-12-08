package com.qwikkspell.partygamesapi.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScoreApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetAllScores() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/scores")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testAddScore() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "scoreValue": 1200,
                    "achievedAt": "2024-11-29T10:00:00",
                    "playerUuid": "player-uuid",
                    "gameName": "MazeRunner"
                }
            """)
                .when()
                .post("/api/scores")
                .then()
                .statusCode(201)
                .body("scoreValue", equalTo(1200))
                .body("playerUuid", equalTo("player-uuid"))
                .body("gameName", equalTo("MazeRunner"));
    }

    @Test
    void testGetTopScores() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/scores/top?gameName=MazeRunner&limit=5")
                .then()
                .statusCode(200)
                .body("size()", lessThanOrEqualTo(5));
    }

    @Test
    void testDeleteScore() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/scores/1")
                .then()
                .statusCode(204);
    }
}
