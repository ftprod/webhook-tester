package org.sample;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class WebhookResourceTest {

    @Test
    public void testGet() {
        given()
                .when().get("/webhook-handler")
                .then()
                .statusCode(200);
    }
    @Test
    public void testPost() {
        given()
          .when().body("{d:d}").header("x-esante-api-hmac-sha256", "sha256=1234").post("/webhook-handler")
          .then()
             .statusCode(204);
    }

}