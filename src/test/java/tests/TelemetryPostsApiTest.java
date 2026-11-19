package tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import base.BaseTest;
import io.restassured.response.Response;
import models.TelemetryPost;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TelemetryPostsApiTest extends BaseTest {

    @Test(description = "Verify GET /posts/1 returns 200 OK and valid payload fields")
    public void verifyGetSinglePost() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"))
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyOrNullString()))
                .extract()
                .response();

        String title = response.jsonPath().getString("title");
        System.out.println("Fetched Title: " + title);
        Assert.assertFalse(title.isBlank(), "Response title blank नसावा!");
    }

    @Test(description = "Verify POST /posts creates a new post using POJO")
    public void verifyCreatePost() {
        TelemetryPost requestPayload = new TelemetryPost(
                101,
                "Vehicle Telemetry Alert",
                "Battery voltage dropped below threshold"
        );

        TelemetryPost createdPost = given()
                .spec(requestSpec)
                .body(requestPayload) // Jackson POJO -> JSON (Serialization)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .time(lessThan(5000L))
                .extract()
                .as(TelemetryPost.class); // Jackson JSON -> POJO (Deserialization)

        System.out.println("Created Post ID: " + createdPost.getId());
        Assert.assertEquals(createdPost.getUserId(), 101);
        Assert.assertEquals(createdPost.getTitle(), "Vehicle Telemetry Alert");
        Assert.assertEquals(createdPost.getBody(), "Battery voltage dropped below threshold");
        Assert.assertTrue(createdPost.getId() > 0, "नवीन ID जनरेट व्हायला हवा!");
    }
    @Test(description = "Verify PUT /posts/1 updates an existing post using POJO")
    public void verifyUpdatePost() {
        TelemetryPost updatedPayload = new TelemetryPost(
                101,
                "Updated Telemetry Alert",
                "Battery voltage restored to normal"
        );
        updatedPayload.setId(1);

        TelemetryPost updatedPost = given()
                .spec(requestSpec)
                .body(updatedPayload)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .extract()
                .as(TelemetryPost.class);

        System.out.println("Updated Title: " + updatedPost.getTitle());
        Assert.assertEquals(updatedPost.getId(), 1);
        Assert.assertEquals(updatedPost.getUserId(), 101);
        Assert.assertEquals(updatedPost.getTitle(), "Updated Telemetry Alert");
        Assert.assertEquals(updatedPost.getBody(), "Battery voltage restored to normal");
    }

    @Test(description = "Verify DELETE /posts/1 returns 200 OK")
    public void verifyDeletePost() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200)
                .time(lessThan(5000L));

        System.out.println("Post deleted successfully (status 200).");
    }
    @Test(description = "Verify GET /posts/99999 returns 404 Not Found for non-existent post")
    public void verifyNonExistentPostReturns404() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404)
                .time(lessThan(5000L));

        System.out.println("Verified 404 Not Found for non-existent post ID 99999.");
    }
}