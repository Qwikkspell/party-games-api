package com.qwikkspell.partygamesapi.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetPlayerByUuid() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/players/player-uuid")
                .then()
                .statusCode(200)
                .body("uuid", equalTo("51952f43-27c7-4275-a29b-55c8d2c52a2b"))
                .body("username", equalTo("Qwikkspell"));
    }

    @Test
    void testCreatePlayer() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "uuid": "player-uuid",
                    "username": "PlayerOne",
                    "joinedAt": "2024-11-29T10:00:00"
                }
            """)
                .when()
                .post("/api/players")
                .then()
                .statusCode(201)
                .body("uuid", equalTo("player-uuid"))
                .body("username", equalTo("PlayerOne"));
    }

    @Test
    void testDeletePlayer() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/players/player-uuid")
                .then()
                .statusCode(204);
    }
}
